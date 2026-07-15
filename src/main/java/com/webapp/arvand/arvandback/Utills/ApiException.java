package com.webapp.arvand.arvandback.Utills;

public class ApiException extends RuntimeException {

    private final ApiErrorType type;
    private final int status;
    private final String cmsg;

    public ApiException(ApiErrorType type,String cmsg) {
        super(type.getMessage());
        this.type = type;
        this.status = mapStatus(type);
        this.cmsg = cmsg;

    }

    public ApiErrorType getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return type.getCode();
    }

    public String getCmsg() {
        return cmsg;
    }

    private int mapStatus(ApiErrorType type) {
        return switch (type) {
            case INVALID_CREDENTIALS -> 401;
            case UNAUTHORIZED -> 401;
            case TOKEN_EXPIRED -> 401;
            case FORBIDDEN -> 403;
            case NO_PRODUCTS -> 400;
            case LAYOUT_ERROR -> 400;
            case OPT_ERROR -> 400;
            case OPT_VALIDATION_ERROR -> 400;
            case INVALID_PARAM -> 400;
            case SERVER_ERROR -> 400;
            default -> 400;
        };
    }
}
