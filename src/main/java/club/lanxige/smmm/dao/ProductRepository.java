package club.lanxige.smmm.dao;

import club.lanxige.smmm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductName(String productName);
    Optional<Product> findByBarcode(String barcode); // 添加条形码查询方法

    Optional<Product> findByProductNameAndSupplierName(String productName, String supplierName);
}