package club.lanxige.smmm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private String quantity;

    // 保持为 LocalDateTime 类型，与数据库字段匹配
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime restockTime;
}