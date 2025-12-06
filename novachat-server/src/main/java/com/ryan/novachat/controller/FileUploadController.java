package com.ryan.novachat.controller;

import com.ryan.novachat.common.bean.APIResponseBean;
import com.ryan.novachat.common.bean.APIResponseBeanUtil;
import com.ryan.novachat.service.FileUploadService;
import com.ryan.novachat.vo.FileUploadResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Ryan
 * @Date 2025/12/6 10:49
 */
@RestController
@RequestMapping("nova-chat/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    /**
     * 上传文件
     * @param file 文件
     * @param bizType 上传文件业务类型
     */
    @PostMapping("/upload")
    public APIResponseBean<FileUploadResultVO> upload(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("bizType") String bizType) {
        FileUploadResultVO uploadResultVO = fileUploadService.uploadFile(file, bizType);
        return APIResponseBeanUtil.success(uploadResultVO);
    }

}
