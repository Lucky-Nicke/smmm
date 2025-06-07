package club.lanxige.smmm.dto;

import club.lanxige.smmm.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private String message;
    private boolean success;
    private String redirectUrl;
    private User.Role role;
    private UserData data; // 添加数据字段

    // 用户数据内部类
    @Data
    @Builder
    public static class UserData {
        private Integer id;
        private String username;
    }

    public static LoginResponse success(String message, String redirectUrl, User.Role role, Integer userId, String username) {
        return builder()
                .success(true)
                .message(message)
                .redirectUrl(redirectUrl)
                .role(role)
                .data(UserData.builder()
                        .id(userId)
                        .username(username)
                        .build())
                .build();
    }

    public static LoginResponse error(String message) {
        return builder()
                .success(false)
                .message(message)
                .build();
    }
}