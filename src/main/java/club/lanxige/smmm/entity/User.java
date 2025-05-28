package club.lanxige.smmm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class User {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Role {
        WAREHOUSE_MANAGER, CASHIER, SYSTEM_ADMIN, PURCHASER
    }

    public enum Status {
        正常, 锁定
    }

    // 添加JSON反序列化所需的构造函数
    public User() {}

    // 仅用于更新操作的account_id映射
    @JsonProperty("account_id")
    public void setAccountIdForUpdate(Integer accountId) {
        this.id = accountId;
    }
}