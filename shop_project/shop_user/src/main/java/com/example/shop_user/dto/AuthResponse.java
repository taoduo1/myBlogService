package com.example.shop_user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse implements Serializable {

    private String accessToken;
    private String refreshToken;
    private String rememberToken;

}
