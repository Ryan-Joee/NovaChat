package com.ryan.novachat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Ryan
 * @Date 2025/12/5 23:25
 */
@Data
@Entity
@Table(name = "nova_chat_group")
public class GroupEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 群组id
     */
    @Column(name = "group_id")
    private String groupId;

    /**
     * 群名
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * 群头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Version
    @Column(name = "version")
    private Integer version;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (this.createDate == null) {
            this.createDate = now;
        }
    }

}
