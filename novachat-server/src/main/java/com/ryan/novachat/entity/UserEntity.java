package com.ryan.novachat.entity;

import jakarta.persistence.*;
import lombok.Data;


/**
 * @Author Ryan
 * @Date 2025/12/5 16:10
 */

@Data
@Entity
@Table(name = "nova_chat_user")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 用户UUID
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户账号
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 用户昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 用户头像 URL
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 签名
     */
    @Column(name = "signature")
    private String signature;

    /**
     * 用户状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 用户角色
     */
    @Column(name = "role_code")
    private Integer roleCode;

}
