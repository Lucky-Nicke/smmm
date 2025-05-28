package club.lanxige.smmm.controller;

import club.lanxige.smmm.dto.ApiResponse;
import club.lanxige.smmm.entity.User;
import club.lanxige.smmm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ApiResponse<List<User>> listUsers() {
        List<User> users = userService.getAllUsers();
        return ApiResponse.success("查询成功", users);
    }

    @PostMapping("/add")
    public ApiResponse<User> addUser(@RequestBody User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("添加用户时不应指定account_id");
        }
        User savedUser = userService.addUser(user);
        return ApiResponse.success("添加成功", savedUser);
    }

    @PutMapping("/edit")
    public ApiResponse<User> updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("账户ID不能为空");
        }
        User updatedUser = userService.updateUser(user);
        return ApiResponse.success("更新成功", updatedUser);
    }

    @DeleteMapping("/del")
    public ApiResponse<Void> deleteUser(@RequestBody Map<String, Integer> request) {
        Integer accountId = request.get("account_id");
        if (accountId == null) {
            throw new IllegalArgumentException("账户ID不能为空");
        }
        userService.deleteUser(accountId);
        return ApiResponse.success("删除成功", null);
    }
}