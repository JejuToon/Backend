package com.capstone.jejutoon.common.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.capstone.jejutoon.exception.s3.FailedUploadException;
import com.capstone.jejutoon.exception.s3.S3AccessDeniedException;
import com.capstone.jejutoon.exception.s3.S3NotConnectedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final AmazonS3 amazonS3;

    public String uploadImage(MultipartFile file, String path) {
        String fileName = path + UUID.randomUUID() + "_" + file.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
        } catch (AmazonServiceException e) {
            throw new S3AccessDeniedException();
        } catch (AmazonClientException e) {
            throw new S3NotConnectedException();
        } catch (IOException e) {
            throw new FailedUploadException();
        }

        String uploadedUrl = amazonS3.getUrl(bucketName, fileName).toString();
        return URLDecoder.decode(uploadedUrl, StandardCharsets.UTF_8);
    }

    public String uploadBase64Image(String base64Image, String path, String fileName) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        InputStream inputStream = new ByteArrayInputStream(imageBytes);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageBytes.length);
        metadata.setContentType("image/png");

        String fullFileName = path + UUID.randomUUID() + "_" + fileName;

        try {
            amazonS3.putObject(bucketName, fullFileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new S3AccessDeniedException();
        } catch (AmazonClientException e) {
            throw new S3NotConnectedException();
        }

        String uploadedUrl = amazonS3.getUrl(bucketName, fullFileName).toString();
        return URLDecoder.decode(uploadedUrl, StandardCharsets.UTF_8);
    }
}
