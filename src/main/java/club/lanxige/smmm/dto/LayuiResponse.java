package club.lanxige.smmm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayuiResponse<T> {
    private int code;       // Layui要求的状态码，0表示成功
    private String msg;     // 消息提示
    private long count;     // 总记录数
    private List<T> data;   // 数据列表

    // 快速创建成功响应的静态方法
    public static <T> LayuiResponse<T> success(List<T> data, long total) {
        return new LayuiResponse<>(0, "", total, data);
    }
}