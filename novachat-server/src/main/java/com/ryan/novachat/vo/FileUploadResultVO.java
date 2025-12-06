package com.ryan.novachat.vo;

import lombok.Data;


/**
 * @Author Ryan
 * @Date 2025/12/6 16:44
 */
@Data
public class FileUploadResultVO {

    private String url;

    private String name;

    private String type;

    public static FileUploadResultVO of(String url, String name, String type) {
        FileUploadResultVO uploadResultVO = new FileUploadResultVO();
        uploadResultVO.setUrl(url);
        uploadResultVO.setName(name);
        uploadResultVO.setType(type);
        return uploadResultVO;
    }
}
