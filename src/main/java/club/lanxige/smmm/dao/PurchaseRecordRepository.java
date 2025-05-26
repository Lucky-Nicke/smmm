package club.lanxige.smmm.dao;

import club.lanxige.smmm.entity.PurchaseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRecordRepository extends JpaRepository<PurchaseRecord, Integer> {
}