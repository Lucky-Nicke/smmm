package club.lanxige.smmm.service.impl;

import club.lanxige.smmm.dao.UserRepository;
import club.lanxige.smmm.dto.LoginRequest;
import club.lanxige.smmm.dto.LoginResponse;
import club.lanxige.smmm.entity.User;
import club.lanxige.smmm.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (!user.getPassword().equals(request.getPassword())) {
            return LoginResponse.error("密码错误");
        }

        // 检查状态
        if (user.getStatus() == User.Status.锁定) {
            return LoginResponse.error("账号已锁定");
        }

        String redirectUrl = getRedirectUrlByRole(user.getRole());
        // 传递用户ID和用户名
        return LoginResponse.success("登录成功", redirectUrl, user.getRole(), user.getId(), user.getUsername());
    }

    private String getRedirectUrlByRole(User.Role role) {
        return switch (role) {
            case WAREHOUSE_MANAGER -> "/smmm/smmm_html/warehouse/warehouse-frame.html";
            case CASHIER -> "/smmm/smmm_html/cashier/cashier-frame.html";
            case SYSTEM_ADMIN -> "/smmm/smmm_html/admin/admin-frame.html";
            case PURCHASER -> "/smmm/smmm_html/purchaser/purchaser-frame.html";
        };
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("用户名已存在"); // 抛出更明确的异常
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId().longValue())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        // 检查新用户名是否已存在(排除当前用户)
        Optional<User> userWithSameUsername = userRepository.findByUsername(user.getUsername());
        if (userWithSameUsername.isPresent() &&
                !userWithSameUsername.get().getId().equals(user.getId())) {
            throw new DataIntegrityViolationException("用户名已被其他用户使用");
        }

        // 更新字段(只更新有值的字段)
        if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
        if (user.getPassword() != null) existingUser.setPassword(user.getPassword());
        if (user.getRole() != null) existingUser.setRole(user.getRole());
        if (user.getStatus() != null) existingUser.setStatus(user.getStatus());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Integer accountId) {
        userRepository.deleteById(accountId.longValue());
    }
}