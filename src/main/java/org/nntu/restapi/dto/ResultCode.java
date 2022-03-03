package org.nntu.restapi.dto;

public enum ResultCode {

    SUCCESS("Success"),
    EXISTED("Existed"),
    NOT_FOUND("Not found"),
    FAILED("Failed");

    private final String code;

    ResultCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}