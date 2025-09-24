package com.example.roomie_connect_BE.service.serviceImpl;

import com.example.roomie_connect_BE.dto.request.UserRequest;
import com.example.roomie_connect_BE.dto.response.UserResponse;
import com.example.roomie_connect_BE.entity.User;
import com.example.roomie_connect_BE.mapper.UserMapper;
import com.example.roomie_connect_BE.repository.UserRepository;
import com.example.roomie_connect_BE.service.ImageService;
import com.example.roomie_connect_BE.service.JWTService;
import com.example.roomie_connect_BE.service.UserService;
import com.example.roomie_connect_BE.utils.Utilities;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final String BUCKET_NAME_AVAR = "image-avar";
    private final MinioClient minioClient;
    private final JWTService jwtService;
    private final Utilities utilities;
    private final ImageService imageService;



    @Override
    public List<UserResponse> getAllProfilesUser() {
        return userRepository.findAll().stream().map(userMapper::toProfileUserResponse).toList();
    }

    @Override
    public UserResponse getProfileUserByUserId() {
        String userId = utilities.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if ("Empty Avatar".equalsIgnoreCase(user.getAvatar())) {
            return userMapper.toProfileUserResponse(user);
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
        return userMapper.toProfileUserResponse(user);
    }


    @Override
    public UserResponse updateProfileUserByUserId( MultipartFile fileImage) throws Exception {
        User profileUser = userRepository.findById(utilities.getUserId()).get();
        var image = imageService.postImage(fileImage);
        profileUser.setAvatar(image.getImgName());
        userRepository.save(profileUser);
        return userMapper.toProfileUserResponse(profileUser);
    }

    @Override
    public UserResponse updateInformationByUserId(UserRequest userRequest) {
        User profileUser = userRepository.findById(utilities.getUserId()).get();
        profileUser = userMapper.updateProfileUserFromRequest(userRequest, profileUser);
        userRepository.save(profileUser);
        return userMapper.toProfileUserResponse(profileUser);
    }
}
