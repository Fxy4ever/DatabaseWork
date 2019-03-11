package com.ccz.votesystem.config;


import com.ccz.votesystem.interceptor.AuthorityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，如果含有@LoginRequired 注解。决定是否需要登录
        registry.addInterceptor(createInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public AuthorityInterceptor createInterceptor(){
        return new AuthorityInterceptor();
    }
}
