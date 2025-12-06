package com.ryan.novachat.common.enums;

import lombok.Getter;

/**
 * @Author Ryan
 * @Date 2025/12/6 15:59
 */
@Getter
public enum APIErrorCommonEnum {
    FILE_UPLOAD_FAIL(500, "文件上传失败！"),
    PARAM_MISSING(400, "参数不能为空！")
    ;

    /**
     * 异常代码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String message;

    APIErrorCommonEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
