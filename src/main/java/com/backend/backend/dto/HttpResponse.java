package com.backend.backend.dto;

import lombok.Data;

@Data
public class HttpResponse {

    private boolean ok;

    private String message;

    private Object data;

    private String token;

    public HttpResponse(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public HttpResponse(boolean ok, String message, Object data) {
        this.ok = ok;
        this.message = message;
        this.data = data;
    }

    public HttpResponse(boolean ok, String message, Object data, String token) {
        this.ok = ok;
        this.message = message;
        this.data = data;
        this.token = token;
    }
}
