package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.dto.LeaveApplyDTO;
import com.ylb.dto.LeaveAuditDTO;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbLeaveApply;
import com.ylb.mapper.SysUserMapper;
import com.ylb.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "请假管理")
@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Operation(summary = "提交请假申请")
    @PostMapping("/apply")
    public Result<String> apply(@Valid @RequestBody LeaveApplyDTO dto) {
        TbLeaveApply leave = new TbLeaveApply();
        BeanUtils.copyProperties(dto, leave);
        leaveService.createApply(leave);
        return Result.success("提交成功，等待辅导员审批");
    }

    @Operation(summary = "获取我的请假列表")
    @GetMapping("/my")
    public Result<List<TbLeaveApply>> getMyList() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);
        
        return Result.success(leaveService.getStudentList(user.getUserId()));
    }

    @Operation(summary = "辅导员-获取待审批列表")
    @GetMapping("/audit/list")
    public Result<List<TbLeaveApply>> getAuditList(@RequestParam(required = false) Integer status) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);
        // 获取辅导员ID (注意：sys_user 的 ID 就是 counselor_id)

        return Result.success(leaveService.getCounselorList(user.getUserId(), status));
    }

    @Operation(summary = "辅导员-审批")
    @PostMapping("/audit")
    public Result<String> audit(@Valid @RequestBody LeaveAuditDTO auditData) {
        // 前端传 applyId, status, auditRemark
        leaveService.auditApply(auditData.getApplyId(), auditData.getStatus(), auditData.getAuditRemark());
        return Result.success("审批完成");
    }
}