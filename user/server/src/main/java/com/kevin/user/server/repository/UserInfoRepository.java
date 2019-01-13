package com.kevin.user.server.repository;

import com.kevin.user.server.dataobj.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,String> {
    UserInfo findByOpenid(String openId);
}
