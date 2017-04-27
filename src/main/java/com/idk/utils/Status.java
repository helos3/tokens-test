package com.idk.utils;

import javax.servlet.http.HttpServletResponse;

public class Status {
    private int code;
    private String message;


    public static Status of(int code, String message) {
        return new Status(code, message);
    }

    private Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String message() {
        return message;
    }

    public int code() {
        return code;
    }

    public boolean isFailure() {
        return code >= 400 && code <= 526;
    }

    public boolean isSuccess() {
        return code >= 200 && code <= 226;
    }

    public static Status success() {
        return new Status(HttpServletResponse.SC_OK, "Success");
    }
}