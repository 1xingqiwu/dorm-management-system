package com.ylb.service.impl;

import com.ylb.common.exception.BizException;
import com.ylb.common.security.LoginUser;
import com.ylb.common.utils.JwtUtils;
import com.ylb.common.utils.RedisUtils;
import com.ylb.dto.LoginDTO;
import com.ylb.dto.PasswordDTO;
import com.ylb.dto.RegisterDTO;
import com.ylb.dto.ResetPasswordDTO;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbStudent;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbStudentMapper;
import com.ylb.service.AuthService;
import com.ylb.service.SystemConfigService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private TbStudentMapper studentMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SystemConfigService configService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public String login(LoginDTO loginDto) {
        // 1. 调用Security进行认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 2. 认证通过，获取用户信息
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        // 3. 校验身份是否匹配
        Integer dbUserType = loginUser.getSysUser().getUserType();
        Integer inputUserType = loginDto.getUserType();

        // 如果不是管理员(3)，则必须校验勾选的身份是否一致
        if (dbUserType != 3) {
            if (!dbUserType.equals(inputUserType)) {
                throw new BizException("身份类型不匹配，请重新选择身份");
            }
        }

        // 4. 生成Token
        String roleCode = loginUser.getAuthorities().iterator().next().getAuthority();
        String token = jwtUtils.createToken(
                loginUser.getUsername(),
                loginUser.getSysUser().getUserId(),
                roleCode
        );

        // 🟢【关键修改】存入 Redis 实现互踢 (Key: login_token:用户名)
        // 86400秒 = 24小时，建议与 Token 有效期一致
        redisUtils.set("login_token:" + loginUser.getUsername(), token, 86400);

        return token;
    }

    @Override
    public void logout() {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token)) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            try {
                // 解析出用户名，删除 Redis 里的 Key
                Claims claims = jwtUtils.parseToken(token);
                String username = claims.getSubject();
                // 🟢【关键修改】删除唯一令牌
                redisUtils.del("login_token:" + username);
            } catch (Exception e) {
                // 忽略解析错误
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_details", key = "#user.username")
    public void updateProfile(SysUser user) {
        sysUserMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "user_details", allEntries = true)
    public void updatePassword(Long userId, PasswordDTO dto) {
        SysUser user = sysUserMapper.selectById(userId);
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BizException("旧密码错误");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        sysUserMapper.updateById(user);
    }

    @Override
    public void sendResetCode(String username, String email) {
        SysUser user = sysUserMapper.selectByUsername(username);
        if (user == null) {
            throw new BizException("账号不存在");
        }
        if (user.getEmail() == null || !user.getEmail().equals(email)) {
            throw new BizException("该账号未绑定此邮箱，请联系管理员");
        }

        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        // 验证码有效期 5 分钟
        redisUtils.set("reset_code:" + username, code, 300);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("【智慧宿舍】密码重置验证码");
        message.setText("您好，您的验证码是：" + code + "。有效期5分钟，请勿泄露给他人。");
        mailSender.send(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPasswordByCode(ResetPasswordDTO dto) {
        String cacheKey = "reset_code:" + dto.getUsername();
        String cacheCode = (String) redisUtils.get(cacheKey);

        if (cacheCode == null || !cacheCode.equals(dto.getCode())) {
            throw new BizException("验证码错误或已过期");
        }

        SysUser user = sysUserMapper.selectByUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        sysUserMapper.updateById(user);

        redisUtils.del(cacheKey);
        // 清除该用户的登录缓存，强制重新登录
        redisUtils.del("login_token:" + dto.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO dto) {
        if (!configService.isRegisterEnabled()) {
            throw new BizException("系统已关闭注册功能，请联系管理员添加账号");
        }

        SysUser exist = sysUserMapper.selectByUsername(dto.getUsername());
        if (exist != null) {
            throw new BizException("账号已存在");
        }

        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setUserType(0);
        user.setStatus(1);
        sysUserMapper.insert(user);

        TbStudent student = new TbStudent();
        student.setStudentId(user.getUserId());
        student.setStudentNo(dto.getUsername());
        student.setName(dto.getName());
        student.setGender(dto.getGender());
        studentMapper.insert(student);
    }
}