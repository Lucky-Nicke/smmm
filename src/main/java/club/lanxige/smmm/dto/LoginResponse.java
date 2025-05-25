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

    public static LoginResponse success(String message, String redirectUrl, User.Role role) {
        return builder()
                .success(true)
                .message(message)
                .redirectUrl(redirectUrl)
                .role(role)
                .build();
    }

    public static LoginResponse error(String message) {
        return builder()
                .success(false)
                .message(message)
                .build();
    }
}