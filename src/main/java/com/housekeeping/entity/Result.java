package com.housekeeping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    int code;
    String msg;
    Object data;

    public static Result success() {
        return new Result(200, "success", null);
    }
    public static Result success(Object data) {
        return new Result(200, "success", data);
    }
    public static Result success(Object... data) {
        return new Result(200, "success", data);
    }
    public static Result error() {
        return new Result(500,"error",null);
    }
    public static Result error(String msg) {
        return new Result(500, msg, null);
    }
    public static Result error(int code, String msg) {
        return new Result(code, msg, null);
    }
    public static Result error(int code, String msg, Object data) {
        return new Result(code, msg, data);
    }
}
