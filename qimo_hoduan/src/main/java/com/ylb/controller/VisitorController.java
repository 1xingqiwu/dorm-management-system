package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.dto.vo.VisitorVO;
import com.ylb.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "访客管理")
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @Operation(summary = "获取访客列表")
    @GetMapping("/list")
    public Result<List<VisitorVO>> getList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        return Result.success(visitorService.getList(name, status));
    }

    @Operation(summary = "审批或确认离开")
    @PostMapping("/audit")
    public Result<String> audit(@RequestParam Long id, @RequestParam Integer status) {
        // 前端传 id 和 status
        visitorService.auditVisitor(id, status);
        return Result.success("操作成功");
    }
}