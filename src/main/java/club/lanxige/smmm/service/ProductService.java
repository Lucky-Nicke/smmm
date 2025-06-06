package club.lanxige.smmm.service;

import club.lanxige.smmm.dto.CheckoutRequest;
import club.lanxige.smmm.dto.ProductDto;
import club.lanxige.smmm.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto addProduct(ProductDto productDto);
    ProductDto updateProduct(ProductDto productDto);
    void deleteProduct(Integer productId);
    Optional<Product> findByBarcode(String barcode);
    Product updateQuantity(Product product, Integer quantity);

    // 新增方法：获取产品总数
    long getTotalCount();

    List<Product> checkoutProducts(List<CheckoutRequest> checkoutItems);
}