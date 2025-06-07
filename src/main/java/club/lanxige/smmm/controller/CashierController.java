package club.lanxige.smmm.controller;

import club.lanxige.smmm.dto.ApiResponse;
import club.lanxige.smmm.dto.CheckoutRequest;
import club.lanxige.smmm.entity.Product;
import club.lanxige.smmm.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/casher")
public class CashierController {

    private final ProductService productService;

    public CashierController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/checkout/batch")
    public ResponseEntity<ApiResponse<List<Product>>> checkoutBatch(@RequestBody List<CheckoutRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("请求不能为空"));
        }

        try {
            List<Product> checkedOutProducts = productService.checkoutProducts(requests);
            return ResponseEntity.ok(ApiResponse.success("结账成功", checkedOutProducts));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("结账失败：" + e.getMessage()));
        }
    }

    // 原单个商品结账接口保留（可选）
    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<?>> checkout(@RequestBody CheckoutRequest request) {
        // 参数校验
        if (request.getBarcode() == null || request.getQuantity() == null || request.getQuantity() <= 0) {
            return ResponseEntity.badRequest().body(ApiResponse.error("无效的请求参数"));
        }

        // 查询商品
        Optional<Product> productOptional = productService.findByBarcode(request.getBarcode());

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();

        // 检查库存
        if (product.getQuantity() == null || product.getQuantity().intValue() < request.getQuantity()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("库存不足"));
        }

        // 更新库存
        Product updatedProduct = productService.updateQuantity(product, request.getQuantity());

        // 返回成功响应，数据包装在列表中
        return ResponseEntity.ok(ApiResponse.success(
                "结账成功",
                Collections.singletonList(updatedProduct)
        ));
    }

    // 条形码搜索接口（保持不变）
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> searchByBarcode(@RequestParam String barcode) {
        // 参数校验
        if (barcode == null || barcode.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("条形码不能为空"));
        }

        // 查询商品
        Optional<Product> productOptional = productService.findByBarcode(barcode);

        if (productOptional.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success("未找到匹配商品", Collections.emptyList()));
        }

        Product product = productOptional.get();

        // 构建返回结果
        List<Map<String, Object>> resultList = Collections.singletonList(
                Map.of(
                        "productName", product.getProductName(),
                        "unitPrice", product.getUnitPrice(),
                        "barcode", product.getBarcode()
                )
        );

        return ResponseEntity.ok(ApiResponse.success("查询成功", resultList));
    }
}