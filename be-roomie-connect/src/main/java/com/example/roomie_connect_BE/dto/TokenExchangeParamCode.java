package com.example.roomie_connect_BE.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenExchangeParamCode {
    private String grant_type;
    private String client_id;
    private String client_secret;
    private String code;
    private String scope;
}
