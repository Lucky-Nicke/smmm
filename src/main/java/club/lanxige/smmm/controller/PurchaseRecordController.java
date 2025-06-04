package club.lanxige.smmm.controller;

import club.lanxige.smmm.dto.ApiResponse;
import club.lanxige.smmm.dto.LayuiResponse;
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
    public LayuiResponse<PurchaseRecord> list() {
        List<PurchaseRecord> records = purchaseService.listAllRecords();
        return LayuiResponse.success(records, records.size());
    }

    @DeleteMapping("/del")
    public ApiResponse<?> delete(@RequestBody PurchaseRecord record) {
        try {
            purchaseService.deleteRecord(record.getPurchaseId());
            return ApiResponse.success("删除成功", null);
        } catch (Exception e) {
            return ApiResponse.error("删除失败：" + e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ApiResponse<?> edit(@RequestBody PurchaseRecord record) {
        try {
            purchaseService.editRecord(record);
            return ApiResponse.success("修改成功", null);
        } catch (Exception e) {
            return ApiResponse.error("修改失败：" + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ApiResponse<?> add(@RequestBody PurchaseRecord record) {
    try {
            Integer purchaseId = purchaseService.addRecord(record);
            return ApiResponse.success("采购记录添加成功", "/purchase/list", purchaseId );
        } catch (IllegalArgumentException e) {
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("添加失败：" + e.getMessage());
        }
    }
}