package com.ryan.novachat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Ryan
 * @Date 2025/12/5 23:12
 */
@Data
@Entity
@Table(name = "nova_chat_friend")
public class FriendEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 好友id
     */
    @Column(name = "friend_id")
    private String friendId;

    /**
     * 备注名
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 好友状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 好友类型
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 申请时间
     */
    @Column(name = "apply_date")
    private LocalDateTime applyDate;

    /**
     * 处理时间
     */
    @Column(name = "handle_date")
    private LocalDateTime handleDate;

    /**
     * 版本
     */
    @Version
    @Column(name = "version")
    private Integer version;

}
