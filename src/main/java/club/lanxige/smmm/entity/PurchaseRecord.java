package club.lanxige.smmm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private String quantity;
    private String supplierName;
    private Integer purchaserId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseTime;
    private BigDecimal purchaseAllPrice;
    @CreationTimestamp
    @Column(updatable = false) // 禁止更新
    private LocalDateTime createTime;
    @UpdateTimestamp
    private LocalDateTime updateTime;
}