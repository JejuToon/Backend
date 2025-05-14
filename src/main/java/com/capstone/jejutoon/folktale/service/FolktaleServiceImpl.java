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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolktaleServiceImpl implements FolktaleService {

    private final FolktaleRepository folktaleRepository;
    private final LocationRepository locationRepository;
    private final FolktaleCategoryRepository folktaleCategoryRepository;
    private final FolktaleDetailRepository folktaleDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public FolktaleOverviewDto getFolktaleOverview(Long folktaleId) {
        Folktale folktale = folktaleRepository.findById(folktaleId)
                .orElseThrow(() -> new FolktaleNotFoundException(folktaleId));

        List<String> categories = folktaleCategoryRepository.findCategoryNamesByFolktaleId(folktale.getId());
        List<Location> locations = locationRepository.findByFolktaleId(folktale.getId());
        List<Long> folktaleDetailIds = folktaleDetailRepository.findByFolktaleId(folktale.getId());

        return FolktaleConverter.toFolktaleOverviewDto(folktale, locations, categories, folktaleDetailIds);
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

        return getFolktaleList(folktales);
    }

    private PageResponse<FolktaleListDto> getFolktaleList(Page<Folktale> folktales) {
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

        List<FolktaleListDto> folktaleLists = folktales.stream()
                .map(folktale -> FolktaleConverter.toFolktaleListDto(
                        folktale,
                        locationMap.get(folktale.getId()),
                        categoryMap.get(folktale.getId())
                )).toList();

        return new PageResponse<>(folktaleLists, folktales);
    }
}
