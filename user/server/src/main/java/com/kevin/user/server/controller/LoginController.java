package com.kevin.user.server.controller;

import com.kevin.user.server.constants.CookieConstant;
import com.kevin.user.server.constants.RedisConstant;
import com.kevin.user.server.dataobj.UserInfo;
import com.kevin.user.server.eunm.ResultEunm;
import com.kevin.user.server.eunm.RoleEunm;
import com.kevin.user.server.service.UserService;
import com.kevin.user.server.utils.CookieUtil;
import com.kevin.user.server.utils.ResultVoUtil;
import com.kevin.user.server.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplatel;
    /**
     * 买家登录
     * @param openId
     * @param response
     * @return
     */
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

    /**
     * 卖家登录
     * @param openId
     * @param response
     * @return
     */
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam String openId, HttpServletResponse response,
                           HttpServletRequest request){
        Cookie cookie =  CookieUtil.get(request,CookieConstant.TOKEN);
        //cookie 不为空 并且在redis中不过期
        if (cookie != null &&
                StringUtils.isNotEmpty(stringRedisTemplatel.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVoUtil.success();
        }

        //1. 根据openId查询有没有该买家 没有提升用户不存在错误
        UserInfo userInfo =  userService.findByOpenid(openId);
        if (userInfo == null){
            return ResultVoUtil.error(ResultEunm.USER_NOTEXIST);
        }
        //2.判断角色是不是买家 不匹配提示角色 不存在错误
        if (RoleEunm.SELLER.getCode() != userInfo.getRole()){
            return ResultVoUtil.error(ResultEunm.ROLE_ERROR);
        }
        // 3. redis 设置 key=UUID, value = xyz ， xyz其实就是openid
        String token = UUID.randomUUID().toString().replaceAll("-" , "");
        Integer expire = CookieConstant.expire;
        stringRedisTemplatel.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,token),
                openId,expire, TimeUnit.SECONDS);

        //4.cookie里设置 openid

        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);

        return ResultVoUtil.success(null);
    }
}
