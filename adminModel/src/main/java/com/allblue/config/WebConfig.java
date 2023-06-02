package com.allblue.config;

import com.allblue.modules.sys.intercepter.AuthInterceptor;
import com.allblue.modules.sys.intercepter.LoginIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginIntercepter loginIntercepter;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // excludePathPatterns("/login") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
//        registry.addInterceptor(loginIntercepter).addPathPatterns("/**")
//                //.addPathPatterns("*.html")
//                .excludePathPatterns("/login.html")
//                .excludePathPatterns("/register.html")
//                .excludePathPatterns("/")
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/getCode")
//                .excludePathPatterns("/register")
//                .excludePathPatterns("/loginOut")
//                .excludePathPatterns("/register")
//                .excludePathPatterns("/statics/css/*.css")
//                .excludePathPatterns("/statics/js/*.js")
//                .excludePathPatterns("/statics/images/**")
//                .excludePathPatterns("/statics/plugins/**")
//                .excludePathPatterns("/statics/libs/*.js");

//        registry.addInterceptor(authInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns( "/login", "/register");

    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

}
