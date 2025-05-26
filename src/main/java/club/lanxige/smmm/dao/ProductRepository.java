package club.lanxige.smmm.dao;

import club.lanxige.smmm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductName(String productName);

    // 新增方法：根据产品名称和供应商查询产品
    Optional<Product> findByProductNameAndSupplierName(String productName, String supplierName);
}