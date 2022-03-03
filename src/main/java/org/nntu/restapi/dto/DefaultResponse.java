package org.nntu.restapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class DefaultResponse {

    private ResultCode code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DefaultResponse {" +
                "code=" + code +
                ", error='" + error + '\'' +
                '}';
    }
}
