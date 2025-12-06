package com.ryan.novachat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * @Author Ryan
 * @Date 2025/12/6 15:33
 */
@Getter
@AllArgsConstructor
public enum FileBizTypeEnum {
    USER_AVATAR("avatar", "avatar"),
    ;


    /**
     * 上传文件的bizType
     */
    private final String bizType;

    /**
     * 文件上传到Minio的一级目录
     */
    private final String fileDir;

    // 根据 code 查找枚举的工具方法
    public static String matchFileDirByBizType(String bizType) {
        Assert.isTrue(bizType != null, "文件上传失败，不支持该类型的文件！");
        for (FileBizTypeEnum bizTypeEnum : values()) {
            if (bizTypeEnum.getBizType().equals(bizType)) {
                return bizTypeEnum.getFileDir();
            }
        }
        throw new IllegalArgumentException("未知的业务类型"  + bizType);
    }
}
