package com.myblog7.payload;

import lombok.Data;

@Data
public class SignUpDto {//its a pojo class(plain old java object),a class which deals with encapsulation
    private String name;
    private String username;
    private String email;
    private String password;
}

