package com.ylb.controller;


import com.ylb.common.result.Result;
import com.ylb.dto.LoginDTO;
import com.ylb.dto.PasswordDTO;
import com.ylb.dto.ProfileUpdateDTO;
import com.ylb.dto.RegisterDTO;
import com.ylb.dto.ResetPasswordDTO;
import com.ylb.entity.SysUser;
import com.ylb.mapper.SysUserMapper;
import com.ylb.service.AuthService;
import com.ylb.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证管理")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody LoginDTO loginDto) {
        String token = authService.login(loginDto);
        return Result.success(token);
    }

    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<String> logout() {
        authService.logout();
        return Result.success("退出成功");
    }


    @Autowired
    private SysUserMapper sysUserMapper;

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<SysUser> getUserInfo() {
        // 1. 从 Security 上下文获取当前登录用户
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        // 2. 获取用户名 (在 UserDetailsServiceImpl 中存入的是 username)
        String username = (String) authentication.getPrincipal();

        // 3. 查询数据库 (建议调用 Service，这里简化直接调 Mapper 演示)
        // 实际项目中请务必通过 AuthService -> UserMapper
        SysUser user = sysUserMapper.selectByUsername(username);

        // 脱敏处理，不返回密码
        user.setPassword(null);
        return Result.success(user);
    }

    @Operation(summary = "修改个人资料")
    @PostMapping("/update/profile")
    public Result<String> updateProfile(@Valid @RequestBody ProfileUpdateDTO dto) {
        // 强制设置 ID 为当前登录人，防止恶意修改他人
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = sysUserMapper.selectByUsername(username).getUserId();

        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);
        user.setUserId(userId);
        // 补回 username：请求体不含该字段，而 @CacheEvict 以 #user.username 作为缓存 key
        user.setUsername(username);

        authService.updateProfile(user);
        return Result.success("修改成功");
    }

    @Operation(summary = "修改密码")
    @PostMapping("/update/password")
    public Result<String> updatePassword(@Valid @RequestBody PasswordDTO dto) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = sysUserMapper.selectByUsername(username).getUserId();

        authService.updatePassword(userId, dto);
        return Result.success("密码修改成功，请重新登录");
    }

    @Operation(summary = "发送重置验证码")
    @PostMapping("/send-code")
    public Result<String> sendCode(@RequestParam String username, @RequestParam String email) {
        authService.sendResetCode(username, email);
        return Result.success("验证码已发送至邮箱");
    }

    @Operation(summary = "邮箱重置密码")
    @PostMapping("/reset-password")
    public Result<String> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        authService.resetPasswordByCode(dto);
        return Result.success("密码重置成功，请登录");
    }


    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody RegisterDTO dto) {
        authService.register(dto);
        return Result.success("注册成功，请登录");
    }

    // 这是一个公开接口，让前端知道是否显示注册按钮
    @Operation(summary = "获取注册开关状态")
    @GetMapping("/register-status")
    public Result<Boolean> getRegisterStatus() {
        // 这里偷懒直接调 Service，实际开发建议封装 SystemConfigService
        // 我们需要注入 SystemConfigService
        return Result.success(systemConfigService.isRegisterEnabled());
    }
}