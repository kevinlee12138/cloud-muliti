package com.kevin.user.server.eunm;

import lombok.Getter;

@Getter
public enum RoleEunm {
    BUYER(1,"买家"),
    SELLER(2,"卖家")
    ;

    private Integer code;
    private String role;

    RoleEunm(Integer code, String role) {
        this.code = code;
        this.role = role;
    }
}
