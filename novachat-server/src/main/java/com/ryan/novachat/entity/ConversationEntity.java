package com.ryan.novachat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author Ryan
 * @Date 2025/12/5 22:08
 */
@Data
@Entity
@Table(name = "nova_chat_conversation")
public class ConversationEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 用户UUID : 属于谁的会话
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 对方用户ID 或 群ID
     */
    @Column(name = "target_id")
    private String targetId;

    /**
     * 会话类型 （单聊 / 群聊）
     */
    @Column(name = "target_type")
    private Integer targetType;

    /**
     * 最后一条消息ID nova_chat_conversation.last_msg_id = nova_chat_message.id
     */
    @Column(name = "last_msg_id")
    private Long lastMsgId;

    /**
     * 会话摘要
     */
    @Column(name = "last_message")
    private String lastMessage;

    /**
     * 最后一条消息时间
     */
    @Column(name = "last_time")
    private LocalDateTime lastTime;

    /**
     * 未读消息人数
     */
    @Column(name = "unread_count")
    private Integer unreadCount;

    /**
     * 是否置顶
     */
    @Column(name = "is_top")
    private Integer isTop;


}
