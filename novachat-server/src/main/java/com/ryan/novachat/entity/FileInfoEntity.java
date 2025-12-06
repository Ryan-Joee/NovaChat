package com.ryan.novachat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Ryan
 * @Date 2025/12/5 16:27
 */
@Data
@Entity
@Table(name = "nova_chat_file_info")
public class FileInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 上传者用户UUID
     */
    @Column(name = "uploader_id")
    private String uploaderId;

    /**
     * 文件完整objectKey
     */
    @Column(name = "object_key")
    private String objectKey;

    /**
     * 文件URL
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 文件名
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 文件大小
     */
    @Column(name = "size")
    private Long size;

    /**
     * 文件上传时间
     */
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    /**
     * 版本
     */
    @Version
    @Column(name = "version")
    private Integer version;

    @PreUpdate
    public void preUpdate() {
        LocalDateTime now = LocalDateTime.now();
        if (this.uploadDate == null) {
            this.uploadDate = now;
        }
    }

}
