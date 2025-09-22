package com.example.roomie_connect_BE.service;


import com.example.roomie_connect_BE.dto.request.ProfileUserRequest;
import com.example.roomie_connect_BE.dto.response.ProfileUserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileUserService {

    ProfileUserResponse createProfileUser(ProfileUserRequest profileUserRequest);

    ProfileUserResponse getProfileUserByUserId(String userId);

    ProfileUserResponse updateProfileUserByUserId(String userId, MultipartFile fileImage) throws Exception;


    ProfileUserResponse updateInformationByUserId(String userId, ProfileUserRequest profileUserRequest);

    List<ProfileUserResponse> getAllProfilesUser();

}
