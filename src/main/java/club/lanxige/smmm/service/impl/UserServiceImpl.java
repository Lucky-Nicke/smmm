package club.lanxige.smmm.service.impl;

import club.lanxige.smmm.dao.UserRepository;
import club.lanxige.smmm.dto.LoginRequest;
import club.lanxige.smmm.dto.LoginResponse;
import club.lanxige.smmm.entity.User;
import club.lanxige.smmm.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isEmpty()) {
            return LoginResponse.error("用户名不存在");
        }

        User user = userOptional.get();

        // 对比密码（确保实体类有getPassword()方法）
        if (!user.getPassword().equals(request.getPassword())) { // 调用getPassword()
            return LoginResponse.error("密码错误");
        }

        // 检查状态（根据枚举定义选择中文或英文）
        if (user.getStatus() == User.Status.锁定) { // 中文枚举值
            // 如果是英文枚举：if (user.getStatus() == User.Status.LOCKED) {
            return LoginResponse.error("账号已锁定");
        }

        String redirectUrl = getRedirectUrlByRole(user.getRole());
        return LoginResponse.success("登录成功", redirectUrl, user.getRole());
    }

    private String getRedirectUrlByRole(User.Role role) {
        return switch (role) {
            case WAREHOUSE_MANAGER -> "/warehouse/dashboard";
            case CASHIER -> "/cashier/pos";
            case SYSTEM_ADMIN -> "/admin/dashboard";
            case PURCHASER -> "/purchase/orders";
        };
    }
}