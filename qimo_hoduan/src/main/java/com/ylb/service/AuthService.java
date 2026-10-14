package com.ylb.service;

import com.ylb.dto.LoginDTO;
import com.ylb.dto.PasswordDTO;
import com.ylb.dto.RegisterDTO;
import com.ylb.dto.ResetPasswordDTO;
import com.ylb.entity.SysUser;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    String login(LoginDTO loginDto);

    void logout();

    void updateProfile(SysUser user);
    void updatePassword(Long userId, PasswordDTO dto);

    void sendResetCode(String username, String email);
    void resetPasswordByCode(ResetPasswordDTO dto);

    @Transactional(rollbackFor = Exception.class)
    void register(RegisterDTO dto);
}
