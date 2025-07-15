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
    public AuthResponse register(@RequestBody RegisterRequest request) throws Exception {
        String token = userService.register(request.getName(), request.getEmail(), request.getPassword());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) throws Exception {
        String token = userService.login(request.getEmail(), request.getPassword());
        return new AuthResponse(token);
    }
}
