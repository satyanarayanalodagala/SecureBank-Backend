package com.securebank.response;


public class ResponseBuilder {


    public static <T> ApiResponse<T> success(
            String message,
            T data
    ) {


        return new ApiResponse<>(
                "SUCCESS",
                message,
                data
        );

    }



    public static <T> ApiResponse<T> failure(
            String message
    ) {


        return new ApiResponse<>(
                "FAILED",
                message,
                null
        );

    }

}