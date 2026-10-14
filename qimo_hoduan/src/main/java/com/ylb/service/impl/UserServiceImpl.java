package com.ylb.service.impl;

import com.ylb.common.exception.BizException;
import com.ylb.dto.CreateUserDTO;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbCounselor;
import com.ylb.entity.TbDormManager;
import com.ylb.entity.TbStudent;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbCounselorMapper;
import com.ylb.mapper.TbDormManagerMapper;
import com.ylb.mapper.TbStudentMapper;
import com.ylb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    private TbCounselorMapper counselorMapper;
    @Autowired
    private TbDormManagerMapper dormManagerMapper;

    @Override
    public List<SysUser> getUserList(String username, Integer userType) {
        return sysUserMapper.selectList(username, userType);
    }

    @Override
    public void updateUser(SysUser user) {
        // 不允许修改 username 和 password (通过此接口)
        user.setUsername(null);
        user.setPassword(null);
        sysUserMapper.updateById(user);
    }

    @Override
    public void changeStatus(Long userId, Integer status) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setStatus(status);
        sysUserMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long userId) {
        // 逻辑删除
        sysUserMapper.deleteById(userId);
    }
    
    @Override
    public void resetPassword(Long userId, String newPassword) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        sysUserMapper.updateById(user);
    }

    /**
     * 新增账号(仅超级管理员)
     * 1. 角色白名单校验：仅允许创建 学生(0)/辅导员(1)/宿管(2)
     * 2. 账号唯一性校验
     * 3. 写入 sys_user（密码 BCrypt 加密，默认启用）
     * 4. 按角色同步建档：tb_student / tb_counselor / tb_dorm_manager
     * 任一步骤失败则整体回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(CreateUserDTO dto) {
        // 1. 禁止创建管理员账号，越权扩展权限
        Integer userType = dto.getUserType();
        if (userType == null || userType < 0 || userType > 2) {
            throw new BizException("仅支持创建学生、辅导员、宿管账号");
        }

        // 2. 账号唯一性校验
        if (sysUserMapper.selectByUsername(dto.getUsername()) != null) {
            throw new BizException("账号已存在，请更换");
        }

        // 3. 写入 sys_user
        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(dto.getNickname());
        user.setUserType(userType);
        user.setPhone(dto.getPhone());
        user.setEmail(StringUtils.hasText(dto.getEmail()) ? dto.getEmail() : null);
        user.setStatus(1); // 默认启用
        sysUserMapper.insert(user); // useGeneratedKeys 回填 userId

        Long userId = user.getUserId();

        // 4. 按角色同步建档
        switch (userType) {
            case 0 -> {
                TbStudent student = new TbStudent();
                student.setStudentId(userId);
                student.setStudentNo(dto.getUsername()); // 学号默认取账号
                student.setName(dto.getNickname());
                studentMapper.insertByAdmin(student);
            }
            case 1 -> {
                TbCounselor counselor = new TbCounselor();
                counselor.setCounselorId(userId);
                counselor.setWorkNo(dto.getUsername()); // 工号默认取账号
                counselor.setName(dto.getNickname());
                counselor.setPhone(dto.getPhone());
                counselorMapper.insert(counselor);
            }
            case 2 -> {
                TbDormManager manager = new TbDormManager();
                manager.setManagerId(userId);
                manager.setWorkNo(dto.getUsername()); // 工号默认取账号
                manager.setName(dto.getNickname());
                manager.setPhone(dto.getPhone());
                dormManagerMapper.insert(manager);
            }
            default -> throw new BizException("角色类型不合法");
        }
    }
}