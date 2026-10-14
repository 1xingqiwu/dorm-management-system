package com.ylb.service;
import com.ylb.dto.CreateUserDTO;
import com.ylb.entity.SysUser;
import java.util.List;

public interface UserService {
    List<SysUser> getUserList(String username, Integer userType);
    void updateUser(SysUser user);
    void changeStatus(Long userId, Integer status);
    void deleteUser(Long userId);
    void resetPassword(Long userId, String newPassword); // 额外赠送：重置密码功能

    /** 新增账号(仅超级管理员)：写入 sys_user 并按角色同步建档 */
    void createUser(CreateUserDTO dto);
}