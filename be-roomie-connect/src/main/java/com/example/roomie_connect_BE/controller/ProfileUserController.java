package com.example.roomie_connect_BE.controller;


import com.example.roomie_connect_BE.dto.request.ProfileUserRequest;
import com.example.roomie_connect_BE.dto.response.ApiResponse;
import com.example.roomie_connect_BE.service.ProfileUserService;
import com.example.roomie_connect_BE.utils.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileUserController {

    private final ProfileUserService profileUserService;

    @PostMapping("/internal")
    public ApiResponse<Object> createProfileUser(@RequestBody ProfileUserRequest profileUserRequest) {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.CREATE_PROFILE_SUCCESS.getMessage())
                .data(profileUserService.createProfileUser(profileUserRequest))
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<Object> getUserProfile(@PathVariable("userId") String userId) {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.GET_PROFILE_SUCCESS.getMessage())
                .data(profileUserService.getProfileUserByUserId(userId))
                .build();
    }

    @PostMapping("/update/{userId}")
    public ApiResponse<Object> updateUserProfileImage(@PathVariable("userId") String userId,
            @RequestParam("img") MultipartFile imageFile) throws Exception {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.UPDATE_PROFILE_SUCCESS.getMessage())
                .data(profileUserService.updateProfileUserByUserId(userId, imageFile))
                .build();
    }

    @PostMapping("/update-profile/{userId}")
    public ApiResponse<Object> updateUserProfile(@PathVariable("userId") String userId,
                                                 @RequestBody ProfileUserRequest profileUserRequest){
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.UPDATE_PROFILE_SUCCESS.getMessage())
                .data(profileUserService.updateInformationByUserId(userId,profileUserRequest))
                .build();
    }
    @GetMapping
    public ApiResponse<Object> getAllProfilesUser() {
        return ApiResponse.builder()
                .code(1000)
                .message(Notification.GET_ALL_PROFILE_SUCCESS.getMessage())
                .data(profileUserService.getAllProfilesUser())
                .build();
    }

}
