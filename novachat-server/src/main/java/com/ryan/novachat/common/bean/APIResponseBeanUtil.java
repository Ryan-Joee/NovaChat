package com.ryan.novachat.common.bean;

/**
 * @Author Ryan
 * @Date 2025/12/6 09:17
 */
public class APIResponseBeanUtil {

    /**
     * 构建成功响应的结果
     * @param data
     * @return
     */
    public static <T> APIResponseBean<T> success(T data) {
        APIResponseBean<T> apiResponseBean = new APIResponseBean<T>();
        apiResponseBean.setSuccess(true);
        apiResponseBean.setCode(200);
        apiResponseBean.setMsg("OK");
        apiResponseBean.setData(data);
        return apiResponseBean;
    }

    public static APIResponseBean success() {
        return success(null);
    }
}
