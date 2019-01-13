package com.kevin.user.server.eunm;

import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
@Getter
public enum  ResultEunm {
    USER_NOTEXIST(500200,"用户不存在"),
    ROLE_ERROR(500300,"角色错误")
    ;

    private Integer code;
    private String msg;

    ResultEunm(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
