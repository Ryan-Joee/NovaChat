CREATE DATABASE IF NOT EXISTS novachat CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE novachat;

-- ===================================
-- 1. 用户表
-- ===================================
CREATE TABLE `nova_chat_user` (
                                  `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  `user_id`       CHAR(60) NOT NULL COMMENT '用户ID',
                                  `username`      VARCHAR(50) UNIQUE NOT NULL COMMENT '登录账号',
                                  `password`      VARCHAR(100) NOT NULL COMMENT '密码',
                                  `nickname`      VARCHAR(50) NOT NULL DEFAULT '' COMMENT '昵称',
                                  `avatar`        VARCHAR(600) NULL COMMENT '用户头像URL',
                                  `mobile`        VARCHAR(20) UNIQUE NULL COMMENT '手机号',
                                  `signature`     VARCHAR(500) DEFAULT '暂无签名' COMMENT '签名',
                                  `status`        INT NOT NULL DEFAULT 1 COMMENT '用户状态 1正常 2禁用',
                                  `role_code`     INT NOT NULL DEFAULT 1 COMMENT '角色 1普通用户 等等',
                                  `create_by`     CHAR(60) DEFAULT NULL COMMENT '创建人',
                                  `create_date`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_by`     CHAR(60) DEFAULT NULL COMMENT '更新人',
                                  `update_date`   DATETIME DEFAULT NULL COMMENT '更新时间',
                                  `version`       INT DEFAULT '0' COMMENT '版本',
                                  KEY `index_user_id` (`user_id`),
                                  INDEX `idx_mobile` (`mobile`),
                                  INDEX `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ===================================
-- 2. 文件表
-- ===================================
CREATE TABLE `nova_chat_file_info` (
                                       `id`            BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                       `uploader_id`   CHAR(60) NOT NULL COMMENT '上传者用户ID',
                                       `object_key`    VARCHAR(500) NOT NULL COMMENT 'MinIO完整objectKey，如 image/2025/12/02/abc123.jpg',
                                       `file_url`      VARCHAR(600) NOT NULL COMMENT '完整可访问URL（你代码拼接的那种）',
                                       `file_name`     VARCHAR(255) NOT NULL COMMENT '原始文件名，如 头像.jpg',
                                       `size`          BIGINT DEFAULT 0 COMMENT '文件大小字节',
                                       `create_by`     CHAR(60) DEFAULT NULL COMMENT '创建人',
                                       `create_date`   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_by`     CHAR(60) DEFAULT NULL COMMENT '更新人',
                                       `update_date`   DATETIME DEFAULT NULL COMMENT '更新时间',
                                       `version`       INT DEFAULT '0' COMMENT '版本',
                                       UNIQUE KEY `uniq_object_key` (`object_key`),
                                       INDEX `idx_uploader` (`uploader_id`),
                                       INDEX `idx_created` (`create_date` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表（当前阶段最优设计）';

-- ===================================
-- 3. 会话表（核心）
-- ===================================
CREATE TABLE `nova_chat_conversation` (
                                          `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          `user_id`        CHAR(60) NOT NULL COMMENT '属于谁的会话',
                                          `target_id`      CHAR(60) NOT NULL COMMENT '对方用户ID 或 群ID',
                                          `target_type`    TINYINT NOT NULL COMMENT '1单聊 2群聊',
                                          `last_msg_id`    BIGINT UNSIGNED NULL COMMENT '最后一条消息ID', -- last_msg_id = message.id
                                          `last_message`   VARCHAR(300) NULL COMMENT '会话摘要',
                                          `last_time`      DATETIME NULL,
                                          `unread_count`   INT DEFAULT 0,
                                          `is_top`         TINYINT DEFAULT 0 COMMENT '是否置顶 0不置顶 1置顶',
                                          `create_by`      CHAR(60) DEFAULT NULL COMMENT '创建人',
                                          `create_date`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `update_by`      CHAR(60) DEFAULT NULL COMMENT '更新人',
                                          `update_date`    DATETIME DEFAULT NULL COMMENT '更新时间',
                                          `version`        INT DEFAULT '0' COMMENT '版本',
                                          INDEX `index_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话表';

-- ===================================
-- 4. 消息表（单聊群聊共用）
-- ===================================
CREATE TABLE `nova_chat_message` (
                                     `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     `conversation_id` BIGINT UNSIGNED NOT NULL COMMENT '所属会话', -- conversation_id = conversation.id
                                     `sender_id`       CHAR(60) NOT NULL,
                                     `receiver_id`     CHAR(60) NULL COMMENT '单聊填，群聊空',
                                     `group_id`        CHAR(60) NULL COMMENT '群聊填，单聊空',
                                     `msg_type`        TINYINT NOT NULL DEFAULT 1 COMMENT '1文本 2图片 3语音 4视频 5文件 6撤回',
                                     `content`         VARCHAR(1000) NULL,
                                     `file_id`         BIGINT UNSIGNED NULL COMMENT '关联file_info.id',
                                     `is_revoke`       TINYINT DEFAULT 0 COMMENT '是否撤回 0不撤回',
                                     `create_by`       CHAR(60) DEFAULT NULL COMMENT '创建人',
                                     `create_date`     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `update_by`       CHAR(60) DEFAULT NULL COMMENT '更新人',
                                     `update_date`     DATETIME DEFAULT NULL COMMENT '更新时间',
                                     `version`         INT DEFAULT '0' COMMENT '版本',
                                     INDEX `idx_conv_time` (`conversation_id`, `create_date` DESC),
                                     INDEX `idx_sender` (`sender_id`),
                                     INDEX `idx_group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';

-- ===================================
-- 5. 好友关系表
-- ===================================
CREATE TABLE `nova_chat_friend` (
                                    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                    `user_id`    CHAR(60) NOT NULL,
                                    `friend_id`  CHAR(60) NOT NULL,
                                    `remark`     VARCHAR(50) DEFAULT NULL COMMENT '备注名',
                                    `status`     TINYINT DEFAULT 1 COMMENT '1正常 0拉黑',
                                    `type`			 TINYINT DEFAULT 1 COMMENT '1好友关注 2订阅公众号 等',
                                    `apply_date` DATETIME DEFAULT NULL COMMENT '申请时间',
                                    `handle_date` DATETIME DEFAULT NULL COMMENT '处理时间',
                                    `version`    INT DEFAULT '0' COMMENT '版本',
                                    INDEX `idx_user_id` (`user_id`),
                                    INDEX `idx_friend` (`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='好友关系表';

-- ===================================
-- 6. 好友申请表（可选，但建议有）
-- ===================================
CREATE TABLE `nova_chat_friend_apply` (
                                          `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          `from_user_id` CHAR(60) NOT NULL,
                                          `to_user_id`   CHAR(60) NOT NULL,
                                          `remark`       VARCHAR(200) DEFAULT '' COMMENT '申请备注',
                                          `status`       TINYINT DEFAULT 0 COMMENT '0待处理 1同意 2拒绝',
                                          `create_date`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `update_date`  DATETIME DEFAULT NULL COMMENT '更新时间',
                                          `version`      INT DEFAULT '0' COMMENT '版本',
                                          INDEX `idx_from` (`from_user_id`),
                                          INDEX `idx_to` (`to_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='好友申请表';

-- ===================================
-- 7. 群聊表 + 群成员表（极简群聊）
-- ===================================
CREATE TABLE `nova_chat_group` (
                                   `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                   `group_id`     CHAR(60) NOT NULL,
                                   `name`         CHAR(60) NOT NULL,
                                   `avatar`       VARCHAR(600) NULL,
                                   `create_by`    CHAR(60) DEFAULT NULL COMMENT '创建人',
                                   `create_date`  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_by`    CHAR(60) DEFAULT NULL COMMENT '更新人',
                                   `update_date`  DATETIME DEFAULT NULL COMMENT '更新时间',
                                   `version`      INT DEFAULT '0' COMMENT '版本',
                                   INDEX `idx_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群聊表';

CREATE TABLE `nova_chat_group_member` (
                                          `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          `group_id`  BIGINT UNSIGNED NOT NULL,  -- group_id = group.group_id
                                          `user_id`   CHAR(60) NOT NULL,
                                          `role`      TINYINT DEFAULT 3 COMMENT '1群主 2管理员 3成员',
                                          `join_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                                          INDEX `idx_user` (`user_id`),
                                          INDEX `idx_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='群成员表';