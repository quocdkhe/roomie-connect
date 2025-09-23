package com.example.roomie_connect_BE.service.serviceImpl;


import com.example.roomie_connect_BE.dto.response.ImageResponse;
import com.example.roomie_connect_BE.dto.response.UrlImageResponse;
import com.example.roomie_connect_BE.repository.ProfileUserRepository;
import com.example.roomie_connect_BE.service.AIService;
import com.example.roomie_connect_BE.service.ImageService;
import com.example.roomie_connect_BE.utils.Utilities;
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

    private final Utilities utilities;

    private final AIService aiService;

    @Override
    public ImageResponse postImage(MultipartFile imageAvar) throws Exception {
        var booleanExistedBucket = minioClient
                .bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME_AVAR).build());
        if (!booleanExistedBucket) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(BUCKET_NAME_AVAR)
                    .build());
        }

        String imageName = imageAvar.getOriginalFilename() + System.currentTimeMillis();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BUCKET_NAME_AVAR)
                        .object(imageName)
                        .stream(imageAvar.getInputStream(), imageAvar.getSize(), -1)
                        .contentType(imageAvar.getContentType())
                        .build());



        return ImageResponse.builder()
                .imgName(imageName)
                .buckName(BUCKET_NAME_AVAR)
                .build();
    }



    @Override
    public ImageResponse postImageVerify(MultipartFile imageAvar, MultipartFile imageVerify) throws Exception {
        var check = aiService.verifyUserImage(imageAvar,imageVerify);
        if(check == false) return ImageResponse.builder()
                .imgName(check + "")
                .build();

        var booleanExistedBucket = minioClient
                .bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME_AVAR).build());
        if (!booleanExistedBucket) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(BUCKET_NAME_AVAR)
                    .build());
        }


        String imageName = imageAvar.getOriginalFilename() + System.currentTimeMillis();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(BUCKET_NAME_AVAR)
                        .object(imageName)
                        .stream(imageAvar.getInputStream(), imageAvar.getSize(), -1)
                        .contentType(imageAvar.getContentType())
                        .build());

        var userProfile = profileUserRepository.findById(utilities.getUserId()).get();
        userProfile.setAvatar(imageName);
        profileUserRepository.save(userProfile);

        return ImageResponse.builder()
                .imgName(imageName)
                .buckName(BUCKET_NAME_AVAR)
                .build();
    }
}
