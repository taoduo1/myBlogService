package com.example.shop_user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}
