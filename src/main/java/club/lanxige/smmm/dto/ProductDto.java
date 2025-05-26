package club.lanxige.smmm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductDto {
    private Integer productId;
    private String productName;
    private BigDecimal unitPrice;
    private String quantity;

    // 修改为 LocalDate 类型
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate restockTime;
}