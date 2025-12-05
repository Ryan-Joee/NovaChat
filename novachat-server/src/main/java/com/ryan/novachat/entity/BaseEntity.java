package com.ryan.novachat.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * @Author Ryan
 * @Date 2025/12/5 15:44
 */
@MappedSuperclass  // JPA 的“父类映射”: 这个类不是一个独立的数据库表，但它的字段会映射到子类（实体类）对应的数据库表中。
@Data
public class BaseEntity implements Serializable {
    /**
     * implements Serializable: 让对象可以序列化（转成字节），方便存储、网络传输、缓存、Session 使用等场景。
     * 注解：@Column(name = "create_by") : 映射数据库字段
     */

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    /**
     * 注解解释：@Version JPA（Hibernate）提供的 乐观锁（Optimistic Locking） 版本控制注解，用于控制并发更新。
     * 当多个线程同时更新同一条记录时，只允许版本一致的更新成功。
     * Hibernate 会自动在 SQL 中加入版本条件，保证数据不会被覆盖。`
     */
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
