package com.example.roomie_connect_BE.controller;


import com.example.roomie_connect_BE.dto.request.UserRequest;
import com.example.roomie_connect_BE.dto.response.ApiResponse;
import com.example.roomie_connect_BE.dto.response.ImageResponse;
import com.example.roomie_connect_BE.service.ImageService;
import com.example.roomie_connect_BE.service.UserService;
import com.example.roomie_connect_BE.utils.Notification;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileUserController {

    private final UserService profileUserService;
    private final ImageService imageService;

    @GetMapping
    public ApiResponse<Object> getUserProfile() {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.GET_PROFILE_SUCCESS.getMessage())
                .data(profileUserService.getProfileUserByUserId())
                .build();
    }

    @PostMapping("/update")
    public ApiResponse<Object> updateUserProfileImage(
            @RequestParam("img") MultipartFile imageFile) throws Exception {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.UPDATE_PROFILE_SUCCESS.getMessage())
                .data(profileUserService.updateProfileUserByUserId(imageFile))
                .build();
    }

    @PostMapping("/update-profile")
    public ApiResponse<Object> updateUserProfile(
            @RequestBody UserRequest userRequest) {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.UPDATE_PROFILE_SUCCESS.getMessage())
                .data(profileUserService.updateInformationByUserId(userRequest))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<Object> getAllProfilesUser() {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.GET_ALL_PROFILE_SUCCESS.getMessage())
                .data(profileUserService.getAllProfilesUser())
                .build();
    }

    @PostMapping("/upload")
    public ApiResponse<ImageResponse> postImageVerify(
            @RequestParam("imageAvar") MultipartFile imageAvar, @RequestParam("imageVerify") MultipartFile imageVerify
    ) throws Exception {

        return ApiResponse.<ImageResponse>builder()
                .code(1000)
                .message(Notification.POST_IMAGE_SUCCESS.getMessage())
                .data(imageService.postImageVerify(imageAvar, imageVerify))
                .build();
    }
}
