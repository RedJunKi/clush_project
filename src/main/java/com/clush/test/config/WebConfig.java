package com.clush.test.config;

import com.clush.test.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/todos").setViewName("todo-home");
        registry.addViewController("/todos/add").setViewName("todo-post-form");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/signup").setViewName("sign-up");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/calendars").setViewName("calendar");
        registry.addViewController("/calendars/add").setViewName("calendar-post-form");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
                .addPathPatterns("/", "/todos/**", "/calendars/**");
    }
}
