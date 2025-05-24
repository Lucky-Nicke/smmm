package club.lanxige.smmm.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    // 添加所有字段的getter方法
    public Integer getAccountId() { return accountId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public Status getStatus() { return status; }
    public Date getCreateTime() { return createTime; }
    public Date getUpdateTime() { return updateTime; }

    // 可选：添加setter方法（如果需要）
    public void setUsername(String username) { this.username = username; }
    // 其他setter方法...

    public enum Role {
        PURCHASER, CASHIER, WAREHOUSE_MANAGER, SYSTEM_ADMIN
    }

    public enum Status {
        NORMAL, LOCKED
    }
}