package club.lanxige.smmm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private boolean success;
    private String redirectUrl;
    private T data;

    // 快速创建成功响应的静态方法
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, true, null, data);
    }

    public static <T> ApiResponse<T> success(String message, String redirectUrl, T data) {
        return new ApiResponse<>(message, true, redirectUrl, data);
    }

    // 快速创建失败响应的静态方法
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, false, null, null);
    }
}
