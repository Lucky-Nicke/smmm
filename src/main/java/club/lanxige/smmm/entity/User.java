package club.lanxige.smmm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class User { // 建议类名与业务一致，如Account或User
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password; // 字段名和属性名统一为password

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    // 角色枚举（英文，与数据库ENUM一致）
    public enum Role {
        WAREHOUSE_MANAGER, CASHIER, SYSTEM_ADMIN, PURCHASER
    }

    // 状态枚举（中文，与数据库ENUM一致）
    public enum Status {
        正常, 锁定 // 直接对应数据库值
    }


}
