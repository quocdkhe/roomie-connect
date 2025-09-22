package com.example.roomie_connect_BE.service.serviceImpl;

import com.example.roomie_connect_BE.controller.ImageController;
import com.example.roomie_connect_BE.dto.request.ProfileUserRequest;
import com.example.roomie_connect_BE.dto.response.ProfileUserResponse;
import com.example.roomie_connect_BE.dto.response.UrlImageResponse;
import com.example.roomie_connect_BE.entity.ProfileUser;
import com.example.roomie_connect_BE.mapper.ProfileUserMapper;
import com.example.roomie_connect_BE.repository.ProfileUserRepository;
import com.example.roomie_connect_BE.service.ProfileUserService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileUserServiceImpl implements ProfileUserService {

    private final ProfileUserRepository profileUserRepository;
    private final ProfileUserMapper profileUserMapper;
    private final ImageController imageController;
    private final String BUCKET_NAME_AVAR = "image-avar";
    private final MinioClient minioClient;

    @Override
    public ProfileUserResponse createProfileUser(ProfileUserRequest profileUserRequest) {
        ProfileUser profileUser = profileUserMapper.toProfileUser(profileUserRequest);
        profileUser.setAvatar("Empty Avatar");
        profileUserRepository.save(profileUser);
        return profileUserMapper.toProfileUserResponse(profileUser);
    }

    @Override
    public List<ProfileUserResponse> getAllProfilesUser() {
        return profileUserRepository.findAll().stream().map(profileUserMapper::toProfileUserResponse).toList();
    }

    @Override
    public ProfileUserResponse getProfileUserByUserId(String userId) {
        ProfileUser user = profileUserRepository.findById(userId).get();
        String url;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(BUCKET_NAME_AVAR)
                            .object(user.getAvatar().toString())
                            .expiry(604800)
                            .method(Method.GET)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (user.getProvider().equalsIgnoreCase("LOCAL") && !user.getAvatar().equalsIgnoreCase("Empty Avatar")) {
            user.setAvatar(url);
        }

        return profileUserMapper.toProfileUserResponse(user);
    }

    @Override
    public ProfileUserResponse updateProfileUserByUserId(String userId, MultipartFile fileImage) throws Exception {
        ProfileUser profileUser = profileUserRepository.findById(userId).get();
        var image = imageController.postImage(fileImage,userId);
        profileUser.setAvatar(image.getData().getImgName());
        profileUserRepository.save(profileUser);
        return profileUserMapper.toProfileUserResponse(profileUser);
    }

    @Override
    public ProfileUserResponse updateInformationByUserId(String userId, ProfileUserRequest profileUserRequest) {
        ProfileUser profileUser = profileUserRepository.findById(userId).get();
        profileUser = profileUserMapper.updateProfileUserFromRequest(profileUserRequest, profileUser);
        profileUserRepository.save(profileUser);
        profileUserRequest.setId(userId.toString());
        return profileUserMapper.toProfileUserResponse(profileUser);
    }
}
