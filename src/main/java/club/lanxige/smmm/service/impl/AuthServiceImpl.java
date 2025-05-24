package club.lanxige.smmm.service.impl;

import club.lanxige.smmm.dto.request.LoginRequest;
import club.lanxige.smmm.dto.response.AuthResponse;
import club.lanxige.smmm.entity.Account;
import club.lanxige.smmm.exception.CustomException;
import club.lanxige.smmm.repository.AccountRepository;
import club.lanxige.smmm.service.AuthService;
import club.lanxige.smmm.utils.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // 使用Java 21虚拟线程池（建议使用更通用的命名）
    private final ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public AuthServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            // 提交任务到虚拟线程池
            Future<AuthResponse> future = virtualThreadExecutor.submit(() -> {
                // 执行数据库查询和业务逻辑
                Account account = accountRepository.findByUsername(request.getUsername())
                        .orElseThrow(() -> new CustomException("用户名不存在"));

                if (!passwordEncoder.matches(request.getPassword(), account.getPasswordHash())) {
                    throw new CustomException("密码错误");
                }

                if (account.getStatus() == Account.Status.LOCKED) {
                    throw new CustomException("账户已锁定");
                }

                String token = jwtUtils.generateToken(account.getUsername(), account.getRole().name());
                return new AuthResponse(token, account.getUsername(), account.getRole().name());
            });

            // 阻塞获取结果（可根据需要添加超时处理）
            return future.get();

        } catch (InterruptedException e) {
            // 处理线程中断异常
            Thread.currentThread().interrupt();
            throw new CustomException("登录操作被中断");
        } catch (Exception e) { // 合并异常类型，避免捕获冗余的ExecutionException
            // 包装原始异常，保留堆栈信息
            throw new CustomException("登录失败", e);
        } finally {
            // 建议在应用关闭时优雅关闭线程池
            // virtualThreadExecutor.shutdown();
        }
    }
}