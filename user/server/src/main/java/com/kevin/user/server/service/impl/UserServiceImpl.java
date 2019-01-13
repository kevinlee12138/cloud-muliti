package com.kevin.user.server.service.impl;


import com.kevin.user.server.dataobj.UserInfo;
import com.kevin.user.server.repository.UserInfoRepository;
import com.kevin.user.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }
}
