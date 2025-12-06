package com.ryan.novachat.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Ryan
 * @Date 2025/12/6 10:36
 */
@Service
public class SessionService {

    @Autowired
    private HttpServletRequest request;


    /**
     * 往当前用户的 HTTP Session 中存验证码
     * @param sessionKey
     * @param value
     */
    public boolean setSession(String sessionKey, String value) {
        request.getSession().setAttribute(sessionKey, value);
        return true;
    }
}
