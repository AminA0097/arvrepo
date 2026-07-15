package com.webapp.arvand.arvandback.Utills;

public class ApiResponse<T> {
    private boolean success;
    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String msg) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = true;
        res.code = "SUCCESS";
        res.message = msg == null ? "عملیات با موفقیت انجام شد." : msg;
        return res;
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> res = success(null);
        res.data = data;
        return res;
    }

    public static <T> ApiResponse<T> error(String code) {
        ApiResponse<T> res = new ApiResponse<>();
        res.success = false;
        res.code = code;
        return res;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}