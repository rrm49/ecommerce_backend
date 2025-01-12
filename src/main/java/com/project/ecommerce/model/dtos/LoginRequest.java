package com.project.ecommerce.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String userEmailId;
    private String userName;
    private String userPassword;
}