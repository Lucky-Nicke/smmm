package club.lanxige.smmm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PurchaseRecordDto {
    private Integer purchaseId;
    private Integer productId;
    private String productName;
    private String productType;
    private BigDecimal quantity; // 改为BigDecimal类型
    private String unit_of_measurement; // 新增字段
    private String supplierName;
    private String manufacturer; // 新增字段
    private Integer purchaserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 修改日期格式
    private LocalDateTime purchaseTime; // 改为LocalDateTime类型

    private BigDecimal purchaseAllPrice;
}