package com.darkhub.smart_tasker.service;

import com.darkhub.smart_tasker.entity.User;
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

    public String register(String name, String email, String password) throws Exception {
        if (userRepository.findByEmail(email) != null) {
            throw new Exception("Email already registered");
        }

        User newUser = User.builder()
                .name(name)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .dateCreated(LocalDateTime.now())
                .build();

        userRepository.save(newUser);

        // Devolvemos un token para que el usuario quede logueado al registrarse
        return jwtUtil.generateToken(email);
    }

    public String login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("Invalid email or password");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new Exception("Invalid email or password");
        }

        return jwtUtil.generateToken(email);
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
}
