package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "系统管理")
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemConfigService configService;

    @Operation(summary = "获取注册开关")
    @GetMapping("/config/register")
    public Result<Boolean> getRegisterConfig() {
        return Result.success(configService.isRegisterEnabled());
    }

    @Operation(summary = "修改注册开关")
    @PostMapping("/config/register")
    @PreAuthorize("hasRole('ADMIN')") // 只有管理员能改
    public Result<String> updateRegisterConfig(@RequestParam Boolean enabled) {
        configService.setRegisterEnabled(enabled);
        return Result.success("设置成功");
    }
}