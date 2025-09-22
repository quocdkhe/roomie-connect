package com.example.roomie_connect_BE.service.serviceImpl;


import com.example.roomie_connect_BE.dto.response.ImageResponse;
import com.example.roomie_connect_BE.dto.response.UrlImageResponse;
import com.example.roomie_connect_BE.repository.ProfileUserRepository;
import com.example.roomie_connect_BE.service.ImageService;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final String BUCKET_NAME_AVAR = "image-avar";

    private final MinioClient minioClient;

    private final ProfileUserRepository profileUserRepository;


    @Override
    public ImageResponse postImage(MultipartFile fileImage, String userId) throws Exception {
        var booleanExistedBucket = minioClient
                .bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME_AVAR).build());
        if (!booleanExistedBucket) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(BUCKET_NAME_AVAR)
                    .build());
        }
        String imageName = fileImage.getOriginalFilename() + System.currentTimeMillis();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BUCKET_NAME_AVAR)
                        .object(imageName)
                        .stream(fileImage.getInputStream(), fileImage.getSize(), -1)
                        .contentType(fileImage.getContentType())
                        .build());

        var userProfile = profileUserRepository.findById(userId).get();
        userProfile.setAvatar(imageName);
        profileUserRepository.save(userProfile);

        return ImageResponse.builder()
                .imgName(imageName)
                .buckName(BUCKET_NAME_AVAR)
                .build();
    }


}
