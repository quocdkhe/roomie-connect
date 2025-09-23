package com.example.roomie_connect_BE.service;


import com.example.roomie_connect_BE.dto.request.ProfileUserRequest;
import com.example.roomie_connect_BE.dto.response.ProfileUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileUserService {

    ProfileUserResponse createProfileUser(ProfileUserRequest profileUserRequest);

    ProfileUserResponse getProfileUserByUserId();

    ProfileUserResponse updateProfileUserByUserId(MultipartFile fileImage) throws Exception;


    ProfileUserResponse updateInformationByUserId( ProfileUserRequest profileUserRequest);

    List<ProfileUserResponse> getAllProfilesUser();

}
