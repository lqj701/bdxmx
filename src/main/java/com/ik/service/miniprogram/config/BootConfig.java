package com.ik.service.miniprogram.config;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ik.crm.commons.starter.*;
import com.ik.service.miniprogram.interceptor.UserTokenInterceptor;

@Configuration
@ComponentScan({ "com.ik.service.miniprogram" })
@MapperScan({"com.ik.service.miniprogram.mapper"})
@EnableIkBaseConfigure
@EnableIkLogConfigure
@EnableIkBaseAsyncExecutorConfigure
@EnableIkContextAwareSchedulerConfigure
@EnableIkSwitchScheduledTaskConfigure
@EnableAsync
@EnableScheduling
@EnableIkAutoConfigure
public class BootConfig extends WebMvcConfigurerAdapter {

    @Bean
    UserTokenInterceptor getUserTokenInterceptor() {
        return new UserTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getUserTokenInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(createStringHttpMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/api/static/**").addResourceLocations("classpath:/api/static/");
    }

    private StringHttpMessageConverter createStringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();

        return converter;
    }
}
