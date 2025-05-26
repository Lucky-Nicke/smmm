package club.lanxige.smmm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PurchaseRecordDto {
    private Integer purchaseId;
    private Integer productId;
    private String productName;
    private String productType;
    private String quantity;
    private String supplierName;
    private Integer purchaserId;

    // 修改为 LocalDate 类型
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseTime;

    private BigDecimal purchaseAllPrice;
}