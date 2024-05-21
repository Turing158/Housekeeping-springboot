package com.housekeeping.exception;

import lombok.Data;

@Data
public class ErrorException extends RuntimeException{
    int code;
    String msg;
    public ErrorException(String message) {
        super(message);
        this.msg = message;
    }
    public ErrorException(String message,Exception e) {
        super(message,e);
        this.msg = message;
    }
    public ErrorException(int code,String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }
    public ErrorException(int code,String message,Exception e) {
        super(message,e);
        this.code = code;
        this.msg = message;
    }
}
