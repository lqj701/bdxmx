package com.ik.service.miniprogram.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ik.service.miniprogram.prop.CrosProperty;

@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
     private final Logger log = LoggerFactory.getLogger(CorsConfig.class);

     @Autowired
     private CrosProperty crosProperty;

     @Override
     public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**")
                  .allowedOrigins("*")
                  .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                  .maxAge(3600)
                  .allowCredentials(true);
     }
}
