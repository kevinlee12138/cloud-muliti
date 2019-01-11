package com.kevin.apigateway.eunm;

import lombok.Getter;

@Getter
public enum HttpResultStatus {
    REQUEST_TOO_MUCH(5000,"请求太频繁"),
    ;

    HttpResultStatus(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    private String msg;
    private Integer code;

}
