package club.lanxige.smmm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayuiResponse<T> {
    private int code;       // Layui要求的状态码，0表示成功
    private String msg;     // 消息提示
    private long count;     // 总记录数
    private List<T> data;   // 数据列表

    // 快速创建成功响应的静态方法 - 支持列表数据
    public static <T> LayuiResponse<T> success(List<T> data, long total) {
        return new LayuiResponse<>(0, "", total, data);
    }

    // 快速创建成功响应的静态方法 - 支持单个对象
    public static <T> LayuiResponse<T> success(String msg, T data) {
        List<T> dataList = data != null ? Collections.singletonList(data) : Collections.emptyList();
        return new LayuiResponse<>(0, msg, dataList.size(), dataList);
    }

    // 快速创建错误响应的静态方法
    public static <T> LayuiResponse<T> error(int code, String msg) {
        return new LayuiResponse<>(code, msg, 0, null);
    }
}