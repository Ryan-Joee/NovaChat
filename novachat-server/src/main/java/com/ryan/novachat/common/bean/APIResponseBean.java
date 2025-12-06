package com.ryan.novachat.common.bean;

import lombok.Data;

/**
 * @Author Ryan
 * @Date 2025/12/6 09:13
 */

@Data
public class APIResponseBean<T> {

    private Boolean success;

    private Integer code;

    private String msg;

    private T data;
}
