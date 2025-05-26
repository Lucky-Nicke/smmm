package club.lanxige.smmm.service;

import club.lanxige.smmm.entity.PurchaseRecord;

import java.util.List;

public interface PurchaseService {
    List<PurchaseRecord> listAllRecords();
    void deleteRecord(Integer purchaseId);
    void editRecord(PurchaseRecord record);
    Integer  addRecord(PurchaseRecord record);
}