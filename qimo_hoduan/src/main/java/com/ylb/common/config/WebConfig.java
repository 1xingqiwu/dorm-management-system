package com.ylb.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射 http://localhost:8080/images/xxx.jpg -> 本地 uploads/xxx.jpg
        String path = "file:" + System.getProperty("user.dir") + "/uploads/";
        registry.addResourceHandler("/images/**").addResourceLocations(path);
    }
}