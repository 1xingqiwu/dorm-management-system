package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.dto.ItemAddDTO;
import com.ylb.dto.ItemUpdateDTO;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbBorrowRecord;
import com.ylb.entity.TbItem;
import com.ylb.mapper.SysUserMapper;
import com.ylb.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "物品管理")
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private SysUserMapper sysUserMapper;

    // --- 物品 CRUD ---
    @GetMapping("/list")
    public Result<List<TbItem>> list() {
        return Result.success(itemService.getItemList());
    }

    @PostMapping("/add")
    public Result<String> add(@Valid @RequestBody ItemAddDTO dto) {
        TbItem item = new TbItem();
        BeanUtils.copyProperties(dto, item);
        itemService.addItem(item);
        return Result.success("添加成功");
    }

    @PostMapping("/update")
    public Result<String> update(@Valid @RequestBody ItemUpdateDTO dto) {
        TbItem item = new TbItem();
        BeanUtils.copyProperties(dto, item);
        itemService.updateItem(item);
        return Result.success("更新成功");
    }

    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        itemService.deleteItem(id);
        return Result.success("删除成功");
    }

    // --- 借用与归还 ---
    @Operation(summary = "学生借用")
    @PostMapping("/borrow/{itemId}")
    public Result<String> borrow(@PathVariable Long itemId) {
        itemService.borrowItem(itemId);
        return Result.success("借用成功，请尽快归还");
    }

    @Operation(summary = "归还物品")
    @PostMapping("/return")
    public Result<String> returnItem(@RequestParam Long recordId, @RequestParam Long itemId) {
        itemService.returnItem(recordId, itemId);
        return Result.success("归还成功");
    }

    @Operation(summary = "查询记录(学生查自己，宿管查所有)")
    @GetMapping("/record/list")
    public Result<List<TbBorrowRecord>> getRecords(@RequestParam(required = false) Long studentId) {
        // 逻辑：如果是学生登录，强制只能查自己
        // 如果是宿管，可以传参数 studentId 查某人，或者不传查所有
        // 简单起见，我们在Controller简单判断一下角色
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);
        
        Long searchId = studentId;
        if (user.getUserType() == 0) { // 学生
            searchId = user.getUserId();
        }
        
        return Result.success(itemService.getRecordList(searchId));
    }
}