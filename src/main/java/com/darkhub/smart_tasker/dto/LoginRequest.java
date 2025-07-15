package com.darkhub.smart_tasker.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
