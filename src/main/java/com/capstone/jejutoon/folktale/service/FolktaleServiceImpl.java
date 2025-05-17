package com.capstone.jejutoon.folktale.service;

import com.capstone.jejutoon.common.dto.response.PageResponse;
import com.capstone.jejutoon.exception.folktale.FolktaleNotFoundException;
import com.capstone.jejutoon.folktale.converter.FolktaleConverter;
import com.capstone.jejutoon.folktale.domain.Folktale;
import com.capstone.jejutoon.folktale.domain.Location;
import com.capstone.jejutoon.folktale.dto.response.FolktaleListDto;
import com.capstone.jejutoon.folktale.dto.response.FolktaleOverviewDto;
import com.capstone.jejutoon.folktale.repository.FolktaleCategoryRepository;
import com.capstone.jejutoon.folktale.repository.FolktaleDetailRepository;
import com.capstone.jejutoon.folktale.repository.FolktaleRepository;
import com.capstone.jejutoon.folktale.repository.LocationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FolktaleServiceImpl implements FolktaleService {

    private final FolktaleRepository folktaleRepository;
    private final LocationRepository locationRepository;
    private final FolktaleCategoryRepository folktaleCategoryRepository;
    private final FolktaleDetailRepository folktaleDetailRepository;

    private static final double RADIUS = 6371;

    @Override
    @Transactional(readOnly = true)
    public FolktaleOverviewDto getFolktaleOverview(Long folktaleId) {
        Folktale folktale = folktaleRepository.findById(folktaleId)
                .orElseThrow(() -> new FolktaleNotFoundException(folktaleId));

        Double averageScore = folktaleRepository.findAverageScoreByFolktaleId(folktale.getId());
        if (averageScore == null) {
            averageScore = 0.0;
        }

        List<String> categories = folktaleCategoryRepository.findCategoryNamesByFolktaleId(folktale.getId());
        List<Location> locations = locationRepository.findByFolktaleId(folktale.getId());
        List<Long> folktaleDetailIds = folktaleDetailRepository.findByFolktaleId(folktale.getId());

        return FolktaleConverter.toFolktaleOverviewDto(folktale, locations, categories, folktaleDetailIds, averageScore);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FolktaleListDto> getFolktaleList(int page, String category) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(
                Sort.Order.asc("title")
        ));

        Page<Folktale> folktales;
        if (category.isEmpty()) {
            folktales = folktaleRepository.findAll(pageable);
        } else {
            folktales = folktaleRepository.findFolktalesByCategory(category, pageable);
        }

        return getFolktaleList(folktales, true);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<FolktaleListDto> getNearByFolktaleList(int page, Double latitude, Double longitude) {
        List<Folktale> allFolktales = folktaleRepository.findAll();
        List<Location> allLocations = locationRepository.findAll();

        // folktaleId 기준으로 위치들을 묶기
        Map<Long, List<Location>> locationMap = allLocations.stream()
                .collect(Collectors.groupingBy(loc -> loc.getFolktale().getId()));

        // 각 설화별로 가장 가까운 위치를 기준으로 거리 계산
        List<FolktaleDistance> sorted = allFolktales.stream()
                .map(folktale -> {
                    List<Location> locations = locationMap.getOrDefault(folktale.getId(), List.of());
                    double minDistance = locations.stream()
                            .mapToDouble(loc -> calculateDistance(latitude, longitude, loc.getLatitude(), loc.getLongitude()))
                            .min()
                            .orElse(Double.MAX_VALUE);
                    return new FolktaleDistance(folktale, minDistance);
                })
                .sorted(Comparator.comparingDouble(FolktaleDistance::getDistance))
                .toList();

        int pageSize = 10;
        int fromIndex = page * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, sorted.size());

        PageImpl<Folktale> folktales = new PageImpl<>(
                sorted.subList(fromIndex, toIndex).stream()
                        .map(FolktaleDistance::getFolktale)
                        .toList()
        );

        return getFolktaleList(folktales, false);
    }


    private PageResponse<FolktaleListDto> getFolktaleList(Page<Folktale> folktales, boolean sortByScore) {
        List<Long> folktaleIds = folktales.stream()
                .map(Folktale::getId)
                .toList();

        List<Location> locations = locationRepository.findByFolktaleIdIn(folktaleIds);
        Map<Long, List<Location>> locationMap = locations.stream()
                .collect(Collectors.groupingBy(location -> location.getFolktale().getId()));

        Map<Long, List<String>> categoryMap = folktaleIds.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        folktaleCategoryRepository::findCategoryNamesByFolktaleId
                ));

        List<Object[]> scoreResults = folktaleRepository.findAverageScoresByFolktaleIds(folktaleIds);
        Map<Long, Double> scoreMap = scoreResults.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> row[1] == null ? 0.0 : ((Number) row[1]).doubleValue()
                ));

        Stream<FolktaleListDto> dtoStream = folktales.stream()
                .map(folktale -> {
                    Long id = folktale.getId();
                    return FolktaleConverter.toFolktaleListDto(
                            folktale,
                            locationMap.get(id),
                            categoryMap.get(id),
                            scoreMap.getOrDefault(id, 0.0)
                    );
                });

        List<FolktaleListDto> folktaleLists = sortByScore
                ? dtoStream.sorted(Comparator.comparing(FolktaleListDto::getScore).reversed()).toList()
                : dtoStream.toList();

        return new PageResponse<>(folktaleLists, folktales);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIUS * c;
    }

    @Getter
    @AllArgsConstructor
    class FolktaleDistance {
        private Folktale folktale;
        private double distance;
    }
}
