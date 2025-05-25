package club.lanxige.smmm.service;

import club.lanxige.smmm.dto.LoginRequest;
import club.lanxige.smmm.dto.LoginResponse;

public interface UserService {
    LoginResponse login(LoginRequest request);
}