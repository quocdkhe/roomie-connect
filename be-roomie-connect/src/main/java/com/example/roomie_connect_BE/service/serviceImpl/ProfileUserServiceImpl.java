package com.example.roomie_connect_BE.service.serviceImpl;

import com.example.roomie_connect_BE.dto.request.ProfileUserRequest;
import com.example.roomie_connect_BE.dto.response.ProfileUserResponse;
import com.example.roomie_connect_BE.dto.response.UrlImageResponse;
import com.example.roomie_connect_BE.entity.ProfileUser;
import com.example.roomie_connect_BE.mapper.ProfileUserMapper;
import com.example.roomie_connect_BE.repository.ProfileUserRepository;
import com.example.roomie_connect_BE.service.ImageService;
import com.example.roomie_connect_BE.service.JWTService;
import com.example.roomie_connect_BE.service.ProfileUserService;
import com.example.roomie_connect_BE.utils.Utilities;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private final String BUCKET_NAME_AVAR = "image-avar";
    private final MinioClient minioClient;
    private final JWTService jwtService;
    private final Utilities utilities;
    private final ImageService imageService;



    @Override
    public List<ProfileUserResponse> getAllProfilesUser() {
        return profileUserRepository.findAll().stream().map(profileUserMapper::toProfileUserResponse).toList();
    }

    @Override
    public ProfileUserResponse getProfileUserByUserId() {
        String userId = utilities.getUserId();
        ProfileUser user = profileUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if ("Empty Avatar".equalsIgnoreCase(user.getAvatar())) {
            return profileUserMapper.toProfileUserResponse(user);
        }
        log.info("avar: " + user.getAvatar());
        String url;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(BUCKET_NAME_AVAR)
                            .object(user.getAvatar())
                            .expiry(604800)
                            .method(Method.GET)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if ("LOCAL".equalsIgnoreCase(user.getProvider()) && !"Empty Avatar".equalsIgnoreCase(user.getAvatar())) {
            user.setAvatar(url);
        }
        return profileUserMapper.toProfileUserResponse(user);
    }


    @Override
    public ProfileUserResponse updateProfileUserByUserId( MultipartFile fileImage) throws Exception {
        ProfileUser profileUser = profileUserRepository.findById(utilities.getUserId()).get();
        var image = imageService.postImage(fileImage);
        profileUser.setAvatar(image.getImgName());
        profileUserRepository.save(profileUser);
        return profileUserMapper.toProfileUserResponse(profileUser);
    }

    @Override
    public ProfileUserResponse updateInformationByUserId(ProfileUserRequest profileUserRequest) {
        ProfileUser profileUser = profileUserRepository.findById(utilities.getUserId()).get();
        profileUser = profileUserMapper.updateProfileUserFromRequest(profileUserRequest, profileUser);
        profileUserRepository.save(profileUser);
        profileUserRequest.setId(utilities.getUserId().toString());
        return profileUserMapper.toProfileUserResponse(profileUser);
    }
}
