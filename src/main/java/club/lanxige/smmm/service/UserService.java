package club.lanxige.smmm.service;

import club.lanxige.smmm.dto.LoginRequest;
import club.lanxige.smmm.dto.LoginResponse;
import club.lanxige.smmm.entity.User;

import java.util.List;

public interface UserService {
    LoginResponse login(LoginRequest request);
    List<User> getAllUsers();
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(Integer accountId);
}