package com.ryan.novachat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;

/**
 * 验证码响应VO
 * @Author Ryan
 * @Date 2025/12/6 09:33
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResultVO {

    private String code;
    private BufferedImage image;
}
