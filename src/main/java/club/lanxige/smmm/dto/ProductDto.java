package club.lanxige.smmm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductDto {
    private Integer productId;
    private String productName;
    private String productType; // 新增字段
    private BigDecimal unitPrice;
    private BigDecimal quantity; // 改为BigDecimal类型
    private String unit_of_measurement; // 新增字段
    private String barcode; // 新增条形码字段
    private String supplierName; // 新增字段
    private String manufacturer; // 新增字段
    private Integer operatorId; // 新增数据库中存在的字段

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime restockTime; // 改为LocalDateTime类型
}