package com.ryan.novachat.controller;

import com.ryan.novachat.common.utils.CaptchaGenerator;
import com.ryan.novachat.service.CaptchaService;
import com.ryan.novachat.vo.CaptchaResultVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author Ryan
 * @Date 2025/12/6 09:11
 */
@Slf4j
@RestController
@RequestMapping("/nova-chat/valid/captcha")
public class CaptchaController {

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 获取验证码
     */
    @GetMapping("/get")
    public void get() {
        // 生成验证码
        CaptchaResultVO captchaResultVO = CaptchaGenerator.generateCaptcha(false);
        // 刷新本地验证码缓存
        captchaService.refreshCaptcha(captchaResultVO.getCode());
        // 响应验证码给客户端
        sendCaptcha(captchaResultVO.getImage());
    }

    private void sendCaptcha(BufferedImage image) {
        response.setContentType("image/png");
        // 告诉浏览器不要缓存验证码
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setDateHeader("Expires", 0);
        try {
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            log.error("poster image create error.", e);
            throw new RuntimeException(e);
        }
    }
}
