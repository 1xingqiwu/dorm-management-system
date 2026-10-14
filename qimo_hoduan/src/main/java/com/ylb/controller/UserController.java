package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.dto.CreateUserDTO;
import com.ylb.dto.UserUpdateDTO;
import com.ylb.entity.SysUser;
import com.ylb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "账号管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "新增账号(仅超级管理员)")
    @PreAuthorize("hasRole('ADMIN')") // 接口级权限拦截：仅超级管理员可调用
    @PostMapping("/create")
    public Result<String> create(@Valid @RequestBody CreateUserDTO dto) {
        userService.createUser(dto);
        return Result.success("账号创建成功");
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public Result<List<SysUser>> list(String username, Integer userType) {
        return Result.success(userService.getUserList(username, userType));
    }

    @Operation(summary = "修改用户信息")
    @PostMapping("/update")
    public Result<String> update(@Valid @RequestBody UserUpdateDTO dto) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(dto, user);
        userService.updateUser(user);
        return Result.success("修改成功");
    }

    @Operation(summary = "修改状态(禁用/启用)")
    @PostMapping("/status")
    public Result<String> changeStatus(@RequestParam Long userId, @RequestParam Integer status) {
        userService.changeStatus(userId, status);
        return Result.success("状态更新成功");
    }

    @Operation(summary = "删除用户")
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success("删除成功");
    }

    @Operation(summary = "重置密码")
    @PostMapping("/reset-pwd")
    public Result<String> resetPwd(@RequestParam Long userId, @RequestParam String password) {
        // 校验一下密码长度
        if (password.length() < 6) {
            return Result.error("密码长度不能少于6位");
        }

        // 调用 Service 修改密码
        userService.resetPassword(userId, password);
        return Result.success("密码重置成功");
    }
}