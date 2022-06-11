package com.itwj.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.*;

@Slf4j
@Configuration
public class WebMvcConfig  extends WebMvcConfigurationSupport {

    /*
    设置静态资源映射：因为8080/backend/index.html不能访问到里面的资源，
    tomcat找到相应的页面(默认情况下只能找static tmplet里面的)

    通过这个配置类 设置静态资源映射，告诉mvc backend目录里面放的是静态资源，直接放行
    @param register
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");

        //   super.addResourceHandlers();前端发送过来的请求是...什么样的  .addResourceLocations出现前面的请求就会找classpath:/backend/里面的文件
    }
}
