package club.lanxige.smmm.service.impl;

import club.lanxige.smmm.entity.PurchaseRecord;
import club.lanxige.smmm.entity.Product;
import club.lanxige.smmm.dao.PurchaseRecordRepository;
import club.lanxige.smmm.dao.ProductRepository;
import club.lanxige.smmm.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // 检查记录是否存在
        Optional<PurchaseRecord> optionalRecord = purchaseRecordRepository.findById(record.getPurchaseId());
        if (optionalRecord.isEmpty()) {
            throw new IllegalArgumentException("采购记录不存在，ID: " + record.getPurchaseId());
        }

        // 更新产品信息
        updateProductFromPurchaseRecord(record);

        // 更新采购记录
        purchaseRecordRepository.save(record);
    }

    @Override
    @Transactional
    public Integer addRecord(PurchaseRecord record) {
        // 更新或创建产品信息
        Product product = updateOrCreateProduct(record);

        // 设置采购记录的产品ID
        record.setProductId(product.getProductId());

        // 保存采购记录
        PurchaseRecord savedRecord = purchaseRecordRepository.save(record);
        return savedRecord.getPurchaseId();
    }

    private Product updateOrCreateProduct(PurchaseRecord record) {
        // 根据商品名称和供应商查询商品是否存在
        Optional<Product> productOptional = productRepository.findByProductNameAndSupplierName(
                record.getProductName(), record.getSupplierName());

        Product product;
        if (productOptional.isEmpty()) {
            // 商品不存在，创建新商品
            product = new Product();
            product.setProductName(record.getProductName());
            product.setProductType(record.getProductType());
            product.setQuantity(record.getQuantity());
            product.setUnit_of_measurement(record.getUnit_of_measurement());
            product.setSupplierName(record.getSupplierName());
            product.setManufacturer(record.getManufacturer());
            product.setRestockTime(record.getPurchaseTime());
            product.setUnitPrice(null); // 默认单价

            if (record.getBarcode() == null || record.getBarcode().isEmpty()) {
                throw new IllegalArgumentException("采购记录必须包含条形码"); // 提前校验
            }
            product.setBarcode(record.getBarcode()); // 赋值给产品实体

            // 保存新商品
            product = productRepository.save(product);
        } else {
            // 商品已存在，更新库存信息
            product = productOptional.get();
            product.setQuantity(product.getQuantity().add(record.getQuantity())); // 使用BigDecimal的add方法累加
            product.setRestockTime(record.getPurchaseTime());
            product.setProductType(record.getProductType());
            product.setUnit_of_measurement(record.getUnit_of_measurement());
            product.setManufacturer(record.getManufacturer());

            // 更新商品信息
            product = productRepository.save(product);
        }

        return product;
    }

    private void updateProductFromPurchaseRecord(PurchaseRecord record) {
        // 根据商品ID查询商品
        Optional<Product> productOptional = productRepository.findById(record.getProductId());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            // 更新商品信息
            product.setProductName(record.getProductName());
            product.setProductType(record.getProductType());
            product.setQuantity(record.getQuantity());
            product.setUnit_of_measurement(record.getUnit_of_measurement());
            product.setSupplierName(record.getSupplierName());
            product.setManufacturer(record.getManufacturer());
            product.setRestockTime(record.getPurchaseTime());

            // 保存更新
            productRepository.save(product);
        }
    }
}