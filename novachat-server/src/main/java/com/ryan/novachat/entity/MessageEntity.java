package com.ryan.novachat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Ryan
 * @Date 2025/12/5 22:54
 */
@Data
@Entity
@Table(name = "nova_chat_message")
public class MessageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 所属会话 nova_chat_message.conversation_id = nova_chat_conversation.id
     */
    @Column(name = "conversation_id")
    private Long conversationId;

    /**
     * 消息发送人id
     */
    @Column(name = "sender_id")
    private String senderId;

    /**
     * 消息接收人id
     */
    @Column(name = "receiver_id")
    private String receiverId;

    /**
     * 消息接收人类型
     */
    @Column(name = "receiver_type")
    private Integer receiverType;

    /**
     * 消息类型
     */
    @Column(name = "message_type")
    private Integer messageType;

    /**
     * 消息内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 是否撤回
     */
    @Column(name = "is_revoke")
    private Integer isRevoke;

    /**
     * 消息发送时间
     */
    @Column(name = "send_date")
    private LocalDateTime sendDate;

    /**
     * 更新时间
     */
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Version
    @Column(name = "version")
    private Integer version;
}
