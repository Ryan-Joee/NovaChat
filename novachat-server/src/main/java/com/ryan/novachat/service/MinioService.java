package com.ryan.novachat.service;

import com.ryan.novachat.common.enums.FileTypeEnum;
import com.ryan.novachat.common.utils.IDGenerateUtil;
import com.ryan.novachat.config.properties.MinioProperties;
import com.ryan.novachat.dto.FileBasicInfoDTO;
import com.ryan.novachat.vo.FileUploadResultVO;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Ryan
 * @Date 2025/12/6 17:17
 */
@Slf4j
@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private FileInfoService fileInfoService;

    private static final String FILE_UPLOAD_PREFIX = "F";

    /**
     * 定义图片和视频的MIME类型列表
     */
    private static final List<String> IMAGE_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/bmp", "image/jpg", "image/webp");
    private static final List<String> VIDEO_MIME_TYPES = Arrays.asList("video/mp4", "video/quicktime", "video/x-msvideo", "video/3gpp", "video/3gpp2", "video/webm", "video/ogg");



    /**
     * 上传文件到minio
     * @param fileDir
     * @param file
     * @param isRandom 是否采用随机文件名
     * @return FileUploadResultVO
     */
    public FileUploadResultVO upload(String fileDir, MultipartFile file, boolean isRandom) {
        String fileName = file.getOriginalFilename();
        String fileId = IDGenerateUtil.createId(FILE_UPLOAD_PREFIX);
        if (isRandom) {
            Assert.notNull(fileName, "文件名不能为空！");
            String extension = fileName.substring(fileName.lastIndexOf("."));
            fileName = fileId + extension;
        }
        try {
            /**
             * 文件上传至Minio
             */
            String objectKey = fileDir + "/" + fileName;
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .object(objectKey)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            String fileUrl = minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + objectKey;
            /**
             * 文件元信息写库
             */
            FileBasicInfoDTO fileBasicInfoDTO = FileBasicInfoDTO.of(objectKey, fileUrl, fileName, file.getSize());
            fileInfoService.fileInfo2DB(fileBasicInfoDTO);

            return FileUploadResultVO.of(fileUrl, fileName, fileType(file));
        } catch (Exception e) {
            log.error("file upload minio exception, file name: {}, source file name: {}", fileName, file.getOriginalFilename());
            return null;
        }
    }

    private String fileType(MultipartFile file) {
        String mimeType = file.getContentType();
        if(IMAGE_MIME_TYPES.contains(mimeType)) {
            return FileTypeEnum.IMAGE.name();
        }
        if (VIDEO_MIME_TYPES.contains(mimeType)) {
            return FileTypeEnum.VIDEO.name();
        }
        return FileTypeEnum.OTHER.name();
    }
}
