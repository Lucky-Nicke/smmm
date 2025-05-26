package club.lanxige.smmm.controller;

import club.lanxige.smmm.entity.PurchaseRecord;
import club.lanxige.smmm.service.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase_record")
public class PurchaseRecordController {

    private final PurchaseService purchaseService;

    public PurchaseRecordController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/list")
    public List<PurchaseRecord> list() {
        return purchaseService.listAllRecords();
    }

    @PostMapping("/del")
    public void delete(@RequestBody PurchaseRecord record) {
        purchaseService.deleteRecord(record.getPurchaseId());
    }

    @PutMapping("/edit")
    public void edit(@RequestBody PurchaseRecord record) {
        purchaseService.editRecord(record);
    }

    @PostMapping("/add")
    public void add(@RequestBody PurchaseRecord record) {
        purchaseService.addRecord(record);
    }
}