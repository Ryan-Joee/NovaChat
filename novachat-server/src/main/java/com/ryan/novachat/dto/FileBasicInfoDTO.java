package com.ryan.novachat.dto;

import lombok.Data;

/**
 * @Author Ryan
 * @Date 2025/12/6 23:21
 */
@Data
public class FileBasicInfoDTO {

    private String objectKey;

    private String fileUrl;

    private String fileName;

    private Long size;


    public static FileBasicInfoDTO of(String objectKey, String fileUrl, String fileName, Long size) {
        FileBasicInfoDTO fileBasicInfoDTO = new FileBasicInfoDTO();
        fileBasicInfoDTO.setObjectKey(objectKey);
        fileBasicInfoDTO.setFileUrl(fileUrl);
        fileBasicInfoDTO.setFileName(fileName);
        fileBasicInfoDTO.setSize(size);
        return fileBasicInfoDTO;
    }

}
