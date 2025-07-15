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
    public JSendResponse<String> register(@RequestBody RegisterRequest request) {
        userService.register(request.getName(), request.getEmail(), request.getPassword());
        return JSendResponse.success("Registro exitoso");
    }

    @PostMapping("/login")
    public JSendResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = userService.login(request.getEmail(), request.getPassword());
        return JSendResponse.success(authResponse);
    }
}
