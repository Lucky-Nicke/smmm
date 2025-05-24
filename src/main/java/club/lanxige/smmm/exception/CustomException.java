package club.lanxige.smmm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CustomException extends RuntimeException {
    // 原有的单参数构造函数
    public CustomException(String message) {
        super(message);
    }

    // 添加接受message和cause的构造函数
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}