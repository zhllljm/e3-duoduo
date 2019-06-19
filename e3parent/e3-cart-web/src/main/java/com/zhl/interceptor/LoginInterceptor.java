package com.zhl.interceptor;

import com.zhl.pojo.TbUser;
import com.zhl.sso.service.TokenService;
import com.zhl.utils.CookieUtils;
import com.zhl.utils.E3Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录处理拦截器
 */

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //前处理
        // 执行handler之前执行方法
        //返回true，放行 false：拦截
        //从cookie中取token
        String token = CookieUtils.getCookieValue(request, "token");
        //如果没有token，未登录状态，直接放行
        if (StringUtils.isBlank(token)) {
            return true;
        }
        //取到token，需要调用sso系统的服务，根据token取用户信息
        E3Result e3Result =tokenService.getUserByToken(token);
        //如果没有用户信息。登录过期，直接放行
        if (e3Result.getStatus() != 200){
            return  true;
        }
        //取到用户信息，登录状态
        TbUser tbUser = (TbUser) e3Result.getData();
        //把用户信息放到request中，只需要在controller中判断request中是否包含user信息，执行
        request.setAttribute("user",tbUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //handler执行之后，返回ModelAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 完成处理，返回ModelAndView之后
        //  可以再次处理异常
    }
}
