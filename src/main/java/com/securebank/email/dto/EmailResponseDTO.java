package com.securebank.email.dto;

public class EmailResponseDTO {

    private boolean success;
    private String message;

    public EmailResponseDTO() {
    }

    public EmailResponseDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}