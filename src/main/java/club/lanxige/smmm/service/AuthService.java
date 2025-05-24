package club.lanxige.smmm.service;

import club.lanxige.smmm.dto.request.LoginRequest;
import club.lanxige.smmm.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
