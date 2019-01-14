package com.kevin.apigateway.filter;

import com.kevin.apigateway.constants.CookieConstant;
import com.kevin.apigateway.constants.RedisConstant;
import com.kevin.apigateway.utils.CookieUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class AuthFilter extends ZuulFilter {

    @Autowired
   private StringRedisTemplate stringRedisTemplate;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1; // 5 - 1 ,放在优先级为5的过滤器之前
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        /**
          * 方法执行前，需要涉及到的服务忽略敏感头，使其可以传递 cookie
          * /order/create 只能买家访问 (Cookie 里有 openid)
          * /order/finish 只能卖家访问 (Cookie 里有 token，并且对应 redis中的值)
          * /product/list 都可以访问
          */
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request =  requestContext.getRequest();
        if ("/cloud-order/order/create".equals(request.getRequestURI())){
            Cookie cookie = CookieUtil.get(request, CookieConstant.OPENID);
            if (cookie == null || StringUtils.isEmpty(cookie.getValue())){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            }
        }

         if("/cloud-order/order/finish".equals(request.getRequestURI())){
            Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
            if(cookie == null
                    || StringUtils.isEmpty(cookie.getValue())
                    || StringUtils.isEmpty(stringRedisTemplate.opsForValue().
                        get(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))){
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            }
        }

        return null;
    }
}
