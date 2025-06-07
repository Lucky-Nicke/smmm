package club.lanxige.smmm.service.impl;

import club.lanxige.smmm.dto.CheckoutRequest;
import club.lanxige.smmm.dto.ProductDto;
import club.lanxige.smmm.entity.Product;
import club.lanxige.smmm.dao.ProductRepository;
import club.lanxige.smmm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> checkoutProducts(List<CheckoutRequest> checkoutItems) {
        List<Product> checkedOutProducts = new ArrayList<>();

        for (CheckoutRequest item : checkoutItems) {
            Optional<Product> optionalProduct = findByBarcode(item.getBarcode());
            if (optionalProduct.isEmpty()) {
                throw new IllegalArgumentException("商品不存在，条形码: " + item.getBarcode());
            }

            Product product = optionalProduct.get();

            if (product.getQuantity() == null || product.getQuantity().intValue() < item.getQuantity()) {
                throw new IllegalArgumentException("库存不足，商品: " + product.getProductName());
            }

            // 更新库存
            product.setQuantity(BigDecimal.valueOf(product.getQuantity().intValue() - item.getQuantity()));
            Product updatedProduct = productRepository.save(product);

            // 为了清晰显示购买信息，可以创建一个新对象或使用DTO
            // 但不要修改已经保存到数据库的对象
            checkedOutProducts.add(updatedProduct);
        }

        return checkedOutProducts;
    }

    // 查询所有产品
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 添加产品
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    // 更新产品
    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(productDto.getProductId());
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("产品不存在，ID: " + productDto.getProductId());
        }

        Product productToUpdate = optionalProduct.get();
        // 更新字段
        if (productDto.getProductName() != null) productToUpdate.setProductName(productDto.getProductName());
        if (productDto.getUnitPrice() != null) productToUpdate.setUnitPrice(productDto.getUnitPrice());
        if (productDto.getQuantity() != null) productToUpdate.setQuantity(productDto.getQuantity());
        if (productDto.getSupplierName() != null) productToUpdate.setSupplierName(productDto.getSupplierName());
        if (productDto.getProductType() != null) productToUpdate.setProductType(productDto.getProductType());
        if (productDto.getUnit_of_measurement() != null) productToUpdate.setUnit_of_measurement(productDto.getUnit_of_measurement());
        if (productDto.getOperatorId() != null) productToUpdate.setOperatorId(productDto.getOperatorId());
        if (productDto.getManufacturer() != null) productToUpdate.setManufacturer(productDto.getManufacturer());
        if (productDto.getRestockTime() != null) productToUpdate.setRestockTime(productDto.getRestockTime());

        Product updatedProduct = productRepository.save(productToUpdate);
        return convertToDto(updatedProduct);
    }

    // 删除产品
    @Override
    public void deleteProduct(Integer productId) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("产品不存在，ID: " + productId);
        }
        productRepository.deleteById(productId);
    }

    // DTO转实体 - 手动映射
    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setQuantity(productDto.getQuantity());
        product.setSupplierName(productDto.getSupplierName());
        product.setProductType(productDto.getProductType());
        product.setUnit_of_measurement(productDto.getUnit_of_measurement());
        product.setOperatorId(productDto.getOperatorId());
        product.setManufacturer(productDto.getManufacturer());
        product.setRestockTime(productDto.getRestockTime());
        product.setBarcode(productDto.getBarcode()); // 新增条形码映射
        return product;
    }

    // 实体转DTO - 手动映射
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setUnitPrice(product.getUnitPrice());
        dto.setQuantity(product.getQuantity());
        dto.setSupplierName(product.getSupplierName());
        dto.setProductType(product.getProductType());
        dto.setUnit_of_measurement(product.getUnit_of_measurement());
        dto.setOperatorId(product.getOperatorId());
        dto.setManufacturer(product.getManufacturer());
        dto.setRestockTime(product.getRestockTime());
        dto.setBarcode(product.getBarcode()); // 新增条形码映射
        return dto;
    }

    @Override
    public Optional<Product> findByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }

    @Override
    public Product updateQuantity(Product product, Integer quantity) {
        if (product.getQuantity() == null || product.getQuantity().intValue() < quantity) {
            throw new IllegalArgumentException("库存不足");
        }
        product.setQuantity(BigDecimal.valueOf(product.getQuantity().intValue() - quantity));
        return productRepository.save(product);
    }

    // 新增方法：获取产品总数
    @Override
    public long getTotalCount() {
        return productRepository.count();
    }
}