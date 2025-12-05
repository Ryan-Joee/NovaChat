package com.ryan.novachat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Ryan
 * @Date 2025/12/5 23:18
 */
@Data
@Entity
@Table(name = "nova_chat_friend_apply")
public class FriendApplyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 申请人id
     */
    @Column(name = "from_user_id")
    private String fromUserId;

    /**
     * 被申请人id
     */
    @Column(name = "to_user_id")
    private String toUserId;

    /**
     * 申请备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 申请状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Version
    @Column(name = "version")
    private Integer version;

    /**
     * 注解解释：@PrePersist 插入数据库前执行该方法
     */
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (this.createDate == null) {
            this.createDate = now;
        }
    }

    /**
     * 注解解释：@PreUpdate 更新数据库前执行该方法
     */
    @PreUpdate
    public void preUpdate() {
        LocalDateTime now = LocalDateTime.now();
        if (this.updateDate == null) {
            this.updateDate = now;
        }
    }

}
