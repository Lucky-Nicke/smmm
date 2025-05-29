package club.lanxige.smmm.controller;

import club.lanxige.smmm.dto.ApiResponse;
import club.lanxige.smmm.dto.CheckoutRequest;
import club.lanxige.smmm.entity.Product;
import club.lanxige.smmm.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/casher")
public class CashierController {

    private final ProductService productService;

    public CashierController(ProductService productService) {
        this.productService = productService;
    }

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
}