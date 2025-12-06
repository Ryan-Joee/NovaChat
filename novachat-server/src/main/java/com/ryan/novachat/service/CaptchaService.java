package com.ryan.novachat.service;

import com.ryan.novachat.common.constants.WebConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Ryan
 * @Date 2025/12/6 10:30
 */
@Service
public class CaptchaService {

    @Autowired
    private SessionService sessionService;

    public void refreshCaptcha(String code) {
        sessionService.setSession(WebConstant.CAPTCHA_SESSION_KEY, code);
    }
}
