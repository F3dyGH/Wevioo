package com.wevioo.cantine.security.payloads.response;

public class MessageResponse implements AuthApiResponse{
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
