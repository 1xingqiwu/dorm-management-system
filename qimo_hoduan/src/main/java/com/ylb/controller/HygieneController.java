package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.dto.HygieneCheckDTO;
import com.ylb.entity.TbHygieneCheck;
import com.ylb.service.HygieneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "卫生检查")
@RestController
@RequestMapping("/hygiene")
public class HygieneController {

    @Autowired
    private HygieneService hygieneService;

    @Operation(summary = "宿管-录入检查")
    @PostMapping("/add")
    public Result<String> add(@Valid @RequestBody HygieneCheckDTO dto) {
        TbHygieneCheck check = new TbHygieneCheck();
        BeanUtils.copyProperties(dto, check);
        hygieneService.addCheck(check);
        return Result.success("录入成功");
    }

    @Operation(summary = "学生-查看我的卫生记录")
    @GetMapping("/my")
    public Result<List<TbHygieneCheck>> getMyHistory() {
        return Result.success(hygieneService.getMyHistory());
    }

    @Operation(summary = "辅导员-查看班级卫生")
    @GetMapping("/counselor/list")
    public Result<List<TbHygieneCheck>> getCounselorList() {
        return Result.success(hygieneService.getCounselorList());
    }
}