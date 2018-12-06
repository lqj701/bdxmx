package com.bdxmx.miniprogram.service;

import java.util.Map;


public interface LoginService {
    String getCredence(Integer uid);

    Map<String, String> getToken(String credence);

    void deleteUserToken(Integer uid);

    String getTokenByUid(Integer uid);

}
