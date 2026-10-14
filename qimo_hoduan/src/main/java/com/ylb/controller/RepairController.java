package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.dto.RepairSubmitDTO;
import com.ylb.entity.TbRepair;
import com.ylb.mapper.SysUserMapper;
import com.ylb.service.RepairService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repair")
public class RepairController {
    
    @Autowired
    private RepairService repairService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @PostMapping("/submit")
    public Result<String> submit(@Valid @RequestBody RepairSubmitDTO dto) {
        TbRepair repair = new TbRepair();
        BeanUtils.copyProperties(dto, repair);
        repairService.submitRepair(repair);
        return Result.success("报修已提交");
    }

    @GetMapping("/my")
    public Result<List<TbRepair>> getMyList() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = sysUserMapper.selectByUsername(username).getUserId();
        return Result.success(repairService.getMyRepairs(userId));
    }

    @Operation(summary = "宿管-获取所有报修")
    @GetMapping("/list")
    public Result<List<TbRepair>> getAllList(@RequestParam(required = false) Integer status) {
        return Result.success(repairService.getAllRepairs(status));
    }

    @Operation(summary = "宿管-确认维修完成")
    @PostMapping("/finish")
    public Result<String> finish(@RequestParam Long repairId) {
        repairService.finishRepair(repairId);
        return Result.success("操作成功");
    }
}