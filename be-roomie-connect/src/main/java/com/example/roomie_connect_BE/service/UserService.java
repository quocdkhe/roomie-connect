package com.example.roomie_connect_BE.service;



import com.example.roomie_connect_BE.dto.request.UserRequest;
import com.example.roomie_connect_BE.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {


    UserResponse getProfileUserByUserId();

    UserResponse updateProfileUserByUserId(MultipartFile fileImage) throws Exception;


    UserResponse updateInformationByUserId( UserRequest userRequest);

    List<UserResponse> getAllProfilesUser();

}
