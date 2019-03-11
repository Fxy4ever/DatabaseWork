package com.ccz.votesystem.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ccz.votesystem.annotation.PassToken;
import com.ccz.votesystem.annotation.NeedUserToken;
import com.ccz.votesystem.entity.User;
import com.ccz.votesystem.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
/*
token权限验证拦截器
 */
public class AuthorityInterceptor implements HandlerInterceptor {
    @Autowired
    UserServices services;

    //返回true通过，false不通过
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");//尝试从header里面拿到token
        //不是到方法直接允许
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //如果注解带有passToken,直接通过
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required())
                return true;
        }

        //检查有没有需要权限的注解
        if (method.isAnnotationPresent(NeedUserToken.class)) {
            NeedUserToken needUserToken = method.getAnnotation(NeedUserToken.class);

            if (needUserToken.required()) {
                if (token == null)
                    throw new RuntimeException("无token，请重新登陆");

                //token中的userId
                String userId;

                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException e) {
                    throw new RuntimeException("user验证失败，请重新登陆");
                }

                User user = services.getUserById(Integer.parseInt(userId));
                if (user == null)
                    throw new RuntimeException("用户不存在，请重试");

                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("token验证失败");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
