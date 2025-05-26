package club.lanxige.smmm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private BigDecimal unitPrice;
    private BigDecimal quantity; // 改为Integer类型
    private String supplierName; // 新增字段
    private String productType; // 新增字段
    private String unit_of_measurement; // 新增字段
    private String manufacturer; // 新增字段

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime restockTime; // 改为LocalDateTime类型

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime; // 新增字段

    @UpdateTimestamp
    private LocalDateTime updateTime; // 新增字段
}