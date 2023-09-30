package com.myblog7.payload;

import lombok.Data;

@Data
public class LoginDto {//takes jaso object to java dto object
    private String usernameOrEmail;
    private String password;
}
