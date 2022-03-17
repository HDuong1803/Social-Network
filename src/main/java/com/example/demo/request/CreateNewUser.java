package com.example.demo.request;

import lombok.Data;

@Data
public class CreateNewUser {
    private String name;

    private String email;
    private String password;
    private String phone;
}
