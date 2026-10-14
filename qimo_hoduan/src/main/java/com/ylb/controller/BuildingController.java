package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbBuilding;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbBuildingMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "楼栋管理")
@RestController
@RequestMapping("/building")
public class BuildingController {

    @Autowired
    private TbBuildingMapper buildingMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Operation(summary = "获取我管理的楼栋列表")
    @GetMapping("/my")
    public Result<List<TbBuilding>> getMyBuildings() {
        // 1. 获取当前用户
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);
        
        // 2. 查询楼栋 (user_id 即 manager_id)
        return Result.success(buildingMapper.selectByManagerId(user.getUserId()));
    }

    @Operation(summary = "获取楼栋详情")
    @GetMapping("/{id}")
    public Result<TbBuilding> getDetail(@PathVariable Long id) {
        return Result.success(buildingMapper.selectById(id));
    }
}