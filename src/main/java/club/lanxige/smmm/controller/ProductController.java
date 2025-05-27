package club.lanxige.smmm.controller;

import club.lanxige.smmm.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<ProductDto>>> listProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success("查询成功", products));
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