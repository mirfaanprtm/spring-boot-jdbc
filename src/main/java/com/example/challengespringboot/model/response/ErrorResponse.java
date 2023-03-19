package com.example.challengespringboot.model.response;

public class ErrorResponse extends CommonResponse {
    public ErrorResponse(String code, String message){
        super.setCode(code);
        super.setMessage(message);
        super.setStatus("FAILED");
    }
}
