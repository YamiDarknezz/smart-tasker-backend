package com.darkhub.smart_tasker.exception;

import com.darkhub.smart_tasker.dto.JSendResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura ApiException y devuelve fail
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<JSendResponse<String>> handleApiException(ApiException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(JSendResponse.fail(ex.getMessage()));
    }

    // Captura cualquier otra excepción inesperada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSendResponse<String>> handleOtherExceptions(Exception ex) {
        ex.printStackTrace(); // importante para ver stacktrace en logs
        return ResponseEntity
                .internalServerError()
                .body(JSendResponse.error("An unexpected error occurred."));
    }
}
