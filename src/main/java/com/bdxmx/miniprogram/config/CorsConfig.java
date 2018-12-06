package com.bdxmx.miniprogram.config;

import com.bdxmx.miniprogram.prop.CrosProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
     private final Logger log = LoggerFactory.getLogger(CorsConfig.class);

     @Autowired
     private CrosProperty crosProperty;

     @Override
     public void addCorsMappings(CorsRegistry registry) {
     List<String> corsList = crosProperty.getOrigins();
     if (!CollectionUtils.isEmpty(corsList)) {
     String[] array = (String[]) corsList.toArray(new String[corsList.size()]);
     registry.addMapping("/**").allowedOrigins(array);
     } else {
     registry.addMapping("/**").allowedOrigins("*");
     }
     log.info("cors host = {}", corsList);
     }
}
