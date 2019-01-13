package com.kevin.user.server.dataobj;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class UserInfo {

    @Id
    private String id;

    private String username;

    private String password;
    /** 微信openId , 唯一标识 */
    private String openid;
    /** 1: 买家  2：卖家 */
    private Integer role;

}
