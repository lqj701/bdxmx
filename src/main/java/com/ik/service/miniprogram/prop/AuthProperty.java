package com.ik.service.miniprogram.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperty {
    private String scriptToken;

    public String getScriptToken() {
        return scriptToken;
    }

    public void setScriptToken(String scriptToken) {
        this.scriptToken = scriptToken;
    }
}
