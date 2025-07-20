package com.darkhub.smart_tasker.service;

import com.darkhub.smart_tasker.dto.AuthResponse;
import com.darkhub.smart_tasker.entity.User;
import com.darkhub.smart_tasker.exception.ExceptionFactory;
import com.darkhub.smart_tasker.repository.UserRepository;
import com.darkhub.smart_tasker.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(String name, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw ExceptionFactory.invalidField("Name cannot be empty");
        }

        if (password == null || password.length() < 8) {
            throw ExceptionFactory.invalidField("Password must be at least 8 characters");
        }

        if (userRepository.findByEmail(email) != null) {
            throw ExceptionFactory.emailAlreadyRegistered(email);
        }

        User newUser = User.builder()
                .name(name)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .dateCreated(LocalDateTime.now())
                .build();

        userRepository.save(newUser);
    }

    public AuthResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(ExceptionFactory::invalidCredentials);

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw ExceptionFactory.invalidCredentials();
        }

        String token = jwtUtil.generateToken(email);
        return new AuthResponse(token, user.getEmail(), user.getName());
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
