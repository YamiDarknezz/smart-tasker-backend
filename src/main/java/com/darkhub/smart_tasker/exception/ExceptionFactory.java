package com.darkhub.smart_tasker.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;

public class ExceptionFactory {

    public static ApiException emailAlreadyRegistered(String email) {
        return new ApiException("The email '" + email + "' is already registered.", HttpStatus.CONFLICT);
    }

    public static ApiException userNotFoundByEmail(String email) {
        return new ApiException("User with email '" + email + "' not found.", HttpStatus.NOT_FOUND);
    }

    public static ApiException invalidCredentials() {
        return new ApiException("Invalid email or password.", HttpStatus.UNAUTHORIZED);
    }

    public static ApiException unauthorizedAction() {
        return new ApiException("You do not have permission to perform this action.", HttpStatus.FORBIDDEN);
    }

    public static ApiException projectNotFound(String id) {
        return new ApiException("Project with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    public static ApiException taskNotFound(String id) {
        return new ApiException("Task with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    public static ApiException internalError(String detail) {
        return new ApiException("Internal server error: " + detail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ApiException invalidField(String message) {
        return new ApiException(message, HttpStatus.BAD_REQUEST);
    }    

    public static RuntimeException projectNotFound(UUID id) {
        return new RuntimeException("Project not found with ID: " + id);
    }

    public static RuntimeException forbidden(String message) {
        return new RuntimeException("Access denied: " + message);
    }
}
