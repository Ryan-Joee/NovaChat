package com.ryan.novachat.common.exception;

import com.ryan.novachat.common.enums.APIErrorCommonEnum;

/**
 * 业务异常
 * @Author Ryan
 * @Date 2025/12/6 15:55
 */

public class BusinessException extends RuntimeException {

    private Integer code = 500;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(APIErrorCommonEnum apiErrorCommonEnum) {
        super(apiErrorCommonEnum.getMessage());
        this.code = apiErrorCommonEnum.getCode();
    }

    public Integer getCode() {
        return this.code;
    }
}
