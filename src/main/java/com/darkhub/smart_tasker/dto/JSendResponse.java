package com.darkhub.smart_tasker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSendResponse<T> {
    private String status;  // "success", "fail", "error"
    private T data;
    private String message; // opcional: solo en error/fail

    // Para success
    public static <T> JSendResponse<T> success(T data) {
        return new JSendResponse<>("success", data, null);
    }

    // Para fail
    public static <T> JSendResponse<T> fail(String message) {
        return new JSendResponse<>("fail", null, message);
    }

    // Para error (error del servidor)
    public static <T> JSendResponse<T> error(String message) {
        return new JSendResponse<>("error", null, message);
    }
}
