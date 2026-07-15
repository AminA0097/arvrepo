package com.webapp.arvand.arvandback.Utills;

public enum ApiErrorType {

    INVALID_CREDENTIALS("AUTH_001","نام کاربری یا رمز عبور اشتباه است"),
    UNAUTHORIZED("AUTH_002","احراز هویت ناموفق بود"),
    FORBIDDEN("AUTH_003","دسترسی غیرمجاز"),
    TOKEN_EXPIRED("AUTH_004","توکن منقضی شده است"),
    NO_PRODUCTS("PROD_001","محصولی با این مشخصات یافت نشد"),
    LAYOUT_ERROR("LAY_001","خطا در دریافت بنر"),
    OPT_ERROR("OPT_001","خطا در ارسال رمز یکبار مصرف"),
    OPT_VALIDATION_ERROR("OPT_002","کد وارد شده صحیح نمی باشد"),
    INVALID_PARAM("PARAM_001","پارامتر های ورودی صحیح نمی باشد"),
    SERVER_ERROR("SERVER_001","خطا در پاسخگویی سرور");

    private final String code;
    private final String message;

    ApiErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
