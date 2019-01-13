package com.kevin.user.server.service;


import com.kevin.user.server.dataobj.UserInfo;


public interface UserService {

    /**
     * 根据 openid 查询用户信息
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);

}
