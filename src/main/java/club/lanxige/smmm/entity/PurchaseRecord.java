package club.lanxige.smmm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_record")
@Data
public class PurchaseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purchaseId;
    private Integer productId;
    private String productName;
    private String productType;
    private BigDecimal quantity; // 改为Integer类型
    private String unit_of_measurement; // 新增字段
    private String supplierName;
    private String manufacturer; // 新增字段
    private Integer purchaserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 修改日期格式
    private LocalDateTime purchaseTime; // 改为LocalDateTime类型

    private BigDecimal purchaseAllPrice;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}