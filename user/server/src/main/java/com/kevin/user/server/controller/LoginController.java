package com.kevin.user.server.controller;

import com.kevin.user.server.constants.CookieConstant;
import com.kevin.user.server.dataobj.UserInfo;
import com.kevin.user.server.eunm.ResultEunm;
import com.kevin.user.server.eunm.RoleEunm;
import com.kevin.user.server.service.UserService;
import com.kevin.user.server.utils.CookieUtil;
import com.kevin.user.server.utils.ResultVoUtil;
import com.kevin.user.server.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam String openId, HttpServletResponse response){
        //1. 根据openId查询有没有该买家 没有提升用户不存在错误
           UserInfo userInfo =  userService.findByOpenid(openId);
           if (userInfo == null){
               return ResultVoUtil.error(ResultEunm.USER_NOTEXIST);
           }
        //2.判断角色是不是买家 不匹配提示角色 不存在错误
           if (RoleEunm.BUYER.getCode() != userInfo.getRole()){
               return ResultVoUtil.error(ResultEunm.ROLE_ERROR);
           }
        //3.cookie里设置 openid

        CookieUtil.set(response, CookieConstant.OPENID,openId,CookieConstant.expire);

        return ResultVoUtil.success(null);
    }
}
