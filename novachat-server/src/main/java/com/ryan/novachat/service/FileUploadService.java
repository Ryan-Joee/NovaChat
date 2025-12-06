package com.ryan.novachat.service;

import com.ryan.novachat.common.enums.FileBizTypeEnum;
import com.ryan.novachat.common.utils.DateUtil;
import com.ryan.novachat.vo.FileUploadResultVO;
import org.checkerframework.checker.units.qual.min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * @Author Ryan
 * @Date 2025/12/6 16:11
 */
@Service
public class FileUploadService {

    @Autowired
    private MinioService minioService;
    /**
     * 上传文件
     * @param file
     * @param bizType
     * @return
     */
    public FileUploadResultVO uploadFile(MultipartFile file, String bizType) {
        String fileDir = FileBizTypeEnum.matchFileDirByBizType(bizType);
        String timeDir = DateUtil.getDate2String(DateUtil.YYYYMMDD, LocalDateTime.now());
        // 拼接文件上传的目录 ==> 如：avatar/20251206
        fileDir = fileDir + "/" + timeDir;
        FileUploadResultVO uploadResultVO = minioService.upload(fileDir, file, true);
        return uploadResultVO;
    }
}
