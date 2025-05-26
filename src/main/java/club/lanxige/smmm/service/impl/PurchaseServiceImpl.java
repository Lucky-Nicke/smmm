package club.lanxige.smmm.service.impl;

import club.lanxige.smmm.entity.PurchaseRecord;
import club.lanxige.smmm.entity.Product;
import club.lanxige.smmm.dao.PurchaseRecordRepository;
import club.lanxige.smmm.dao.ProductRepository;
import club.lanxige.smmm.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRecordRepository purchaseRecordRepository;
    private final ProductRepository productRepository;

    public PurchaseServiceImpl(PurchaseRecordRepository purchaseRecordRepository,
                               ProductRepository productRepository) {
        this.purchaseRecordRepository = purchaseRecordRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<PurchaseRecord> listAllRecords() {
        return purchaseRecordRepository.findAll();
    }

    @Override
    public void deleteRecord(Integer purchaseId) {
        purchaseRecordRepository.deleteById(purchaseId);
    }

    @Override
    public void editRecord(PurchaseRecord record) {
        purchaseRecordRepository.save(record);
    }

    @Override
    @Transactional
    public Integer addRecord(PurchaseRecord record) {
        String productName = record.getProductName();
        String quantity = record.getQuantity();
        LocalDate purchaseDate = record.getPurchaseTime();
        LocalDateTime restockTime = purchaseDate != null ? purchaseDate.atStartOfDay() : null;

        // 根据商品名称查询商品是否存在
        Optional<Product> productOptional = productRepository.findByProductName(productName);
        Product product;

        if (productOptional.isEmpty()) {
            // 商品不存在，创建新商品（让数据库生成productId）
            product = new Product();
            product.setProductName(productName);
            product.setQuantity(quantity);
            product.setRestockTime(restockTime);
            product.setUnitPrice(BigDecimal.ZERO); // 默认单价

            // 保存新商品并获取生成的productId
            product = productRepository.save(product);

            // 更新采购记录中的productId为新生成的ID
            record.setProductId(product.getProductId());
        } else {
            // 商品已存在，更新库存信息
            product = productOptional.get();
            product.setQuantity(quantity);
            product.setRestockTime(restockTime);
            productRepository.save(product);
        }

        // 保存采购记录（使用已存在或新创建的商品ID）
        purchaseRecordRepository.save(record);

        PurchaseRecord savedRecord = purchaseRecordRepository.save(record);
        return savedRecord.getPurchaseId();
    }
}