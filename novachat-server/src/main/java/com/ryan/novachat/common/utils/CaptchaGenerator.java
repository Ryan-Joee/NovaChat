package com.ryan.novachat.common.utils;

import com.ryan.novachat.vo.CaptchaResultVO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Author Ryan
 * @Date 2025/12/6 09:30
 */
public class CaptchaGenerator {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_COUNT = 4;
    private static final int FONT_SIZE = 28;
    private static final int CODE_X = 20;
    private static final int CODE_Y = 30;

    private static final char[] CHARS =
            "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz".toCharArray();

    public static CaptchaResultVO generateCaptcha(boolean onlyNumber) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 背景
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = new Font("Arial", Font.BOLD, FONT_SIZE);
        g.setFont(font);

        Random random = new Random();

        // 干扰线
        for (int i = 0; i < 20 + random.nextInt(20); i++) {
            g.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));
            g.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT),
                    random.nextInt(WIDTH), random.nextInt(HEIGHT));
        }

        // 随机验证码
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_COUNT; i++) {

            char ch = onlyNumber
                    ? (char) ('0' + random.nextInt(10))
                    : CHARS[random.nextInt(CHARS.length)];

            code.append(ch);

            // 颜色
            g.setColor(new Color(random.nextInt(150), random.nextInt(150), random.nextInt(150)));

            // 随机旋转
            double angle = (random.nextDouble() - 0.5) / 3;
            g.rotate(angle, CODE_X * i + 20, CODE_Y);

            g.drawString(String.valueOf(ch), CODE_X * i + 20, CODE_Y);

            g.rotate(-angle, CODE_X * i + 20, CODE_Y);
        }

        g.dispose();

        return new CaptchaResultVO(code.toString(), image);
    }


    public static void main(String[] args) {
        CaptchaResultVO captchaResultVO = CaptchaGenerator.generateCaptcha(false);
        System.out.println(captchaResultVO.getCode());
    }
}

