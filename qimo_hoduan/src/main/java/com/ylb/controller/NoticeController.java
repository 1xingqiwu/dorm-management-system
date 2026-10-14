package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.dto.NoticeAddDTO;
import com.ylb.dto.NoticeUpdateDTO;
import com.ylb.entity.SysNotice;
import com.ylb.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "公告管理")
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Operation(summary = "获取公告列表")
    @GetMapping("/list")
    public Result<List<SysNotice>> list(@RequestParam(required = false) String title) {
        return Result.success(noticeService.getList(title));
    }

    @Operation(summary = "发布公告")
    @PostMapping("/add")
    public Result<String> add(@Valid @RequestBody NoticeAddDTO dto) {
        SysNotice notice = new SysNotice();
        BeanUtils.copyProperties(dto, notice);
        noticeService.publish(notice);
        return Result.success("发布成功");
    }

    @Operation(summary = "修改公告")
    @PostMapping("/update")
    public Result<String> update(@Valid @RequestBody NoticeUpdateDTO dto) {
        SysNotice notice = new SysNotice();
        BeanUtils.copyProperties(dto, notice);
        noticeService.update(notice);
        return Result.success("修改成功");
    }

    @Operation(summary = "删除公告")
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        noticeService.delete(id);
        return Result.success("删除成功");
    }
}