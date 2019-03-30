package com.ik.service.miniprogram.config;

import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
@ComponentScan({"com.ik.service.miniprogram"})
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
        registry.addInterceptor(getUserTokenInterceptor()).addPathPatterns("/**");
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

    @Bean
    public ServletWebServerFactory servletContainer() {

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {

            @Override
            protected void postProcessContext(Context context) {

                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
        return tomcat;
    }

    /**
     * 让我们的应用支持HTTP是个好想法，但是需要重定向到HTTPS，
     * 但是不能同时在application.properties中同时配置两个connector，
     * 所以要以编程的方式配置HTTP connector，然后重定向到HTTPS connector
     * @return Connector
     */
    private Connector initiateHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(7001); // http端口
        connector.setSecure(false);
        connector.setRedirectPort(450); // application.properties中配置的https端口
        return connector;
    }
}
