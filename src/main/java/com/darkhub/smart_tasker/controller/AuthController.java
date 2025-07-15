package com.darkhub.smart_tasker.controller;

import com.darkhub.smart_tasker.dto.*;
import com.darkhub.smart_tasker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public JSendResponse<AuthResponse> register(@RequestBody RegisterRequest request) {
        String token = userService.register(request.getName(), request.getEmail(), request.getPassword());
        return JSendResponse.success(new AuthResponse(token));
    }

    @PostMapping("/login")
    public JSendResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getEmail(), request.getPassword());
        return JSendResponse.success(new AuthResponse(token));
    }
}
