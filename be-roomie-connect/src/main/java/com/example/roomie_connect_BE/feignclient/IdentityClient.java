package com.example.roomie_connect_BE.feignclient;


import com.example.roomie_connect_BE.dto.*;
import com.example.roomie_connect_BE.dto.response.TokenExchangeResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "identity-client", url = "${idp.token.endpoint}")
public interface IdentityClient {
    @PostMapping(value = "/realms/group1/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam tokenExchangeParam);

    @PostMapping(value = "/admin/realms/group1/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createUser(
            @RequestHeader("authorization") String token
            , @RequestBody UserCreationParam userCreationParam);

    @PostMapping(value = "/realms/group1/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse getUserToken(@RequestHeader("authorization") String token, @QueryMap TokenExchangeParamUser tokenExchangeParamUser);

    @PutMapping(value = "/admin/realms/group1/users/{user-id}/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    Credential changePassword(@RequestHeader("authorization") String token, @RequestBody Credential credential, @PathVariable("user-id") String userId);


    @PostMapping(value = "/admin/realms/group1/users/{user-id}/logout")
    void logoutKeyloak(@RequestHeader("authorization") String token, @PathVariable("user-id") String userId);

    @PostMapping(value = "/realms/group1/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse getTokenOauth2(@QueryMap TokenExchangeOauth2 tokenExchangeOauth2);
}
