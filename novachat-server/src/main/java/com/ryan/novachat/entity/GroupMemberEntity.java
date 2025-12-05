package com.ryan.novachat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Ryan
 * @Date 2025/12/5 23:32
 */
@Data
@Entity
@Table(name = "nova_chat_group_member")
public class GroupMemberEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 群组id  nova_chat_group_member.group_id = nova_chat_group.id
     */
    @Column(name = "group_id")
    private Long groupId;

    /**
     * 群成员用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 群成员角色
     */
    @Column(name = "role")
    private Integer role;

    /**
     * 群成员加群时间
     */
    @Column(name = "join_time")
    private LocalDateTime joinTime;

    @Version
    @Column(name = "version")
    private Integer version;


    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (this.joinTime == null) {
            this.joinTime = now;
        }
    }

}
