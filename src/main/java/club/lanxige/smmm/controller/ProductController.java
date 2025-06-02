package club.lanxige.smmm.controller;

import club.lanxige.smmm.dto.ApiResponse;
import club.lanxige.smmm.dto.LayuiResponse;
import club.lanxige.smmm.dto.ProductDto;
import club.lanxige.smmm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 查询所有产品
    @GetMapping("/list")
    public LayuiResponse<ProductDto> getProducts() {
        // 获取产品列表
        List<ProductDto> productList = productService.getAllProducts();

        // 获取总记录数
        long total = productService.getTotalCount();

        // 使用LayuiResponse封装响应数据
        return LayuiResponse.success(productList, total);
    }

    // 添加产品
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<ProductDto>> addProduct(@RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("添加成功", savedProduct));
    }

    // 更新产品
    @PutMapping("/edit")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productDto);
        return ResponseEntity.ok(ApiResponse.success("更新成功", updatedProduct));
    }

    // 删除产品
    @DeleteMapping("/del")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@RequestBody ProductDto productDto) {
        productService.deleteProduct(productDto.getProductId());
        return ResponseEntity.ok(ApiResponse.success("删除成功", null));
    }
}