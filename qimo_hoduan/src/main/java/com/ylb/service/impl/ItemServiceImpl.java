package com.ylb.service.impl;

import com.ylb.common.exception.BizException;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbBorrowRecord;
import com.ylb.entity.TbItem;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbItemMapper;
import com.ylb.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<TbItem> getItemList() {
        return itemMapper.selectItemList();
    }

    @Override
    public void addItem(TbItem item) {
        // 新增时，可用库存 = 总库存
        item.setAvailableCount(item.getTotalCount());
        itemMapper.insertItem(item);
    }

    @Override
    public void updateItem(TbItem item) {
        itemMapper.updateItem(item);
    }

    @Override
    public void deleteItem(Long itemId) {
        itemMapper.deleteItem(itemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 必须加事务：记录生成+扣库存
    public void borrowItem(Long itemId) {
        // 1. 扣减库存
        int rows = itemMapper.decreaseStock(itemId);
        if (rows <= 0) {
            throw new BizException("手慢了，该物品已被借完！");
        }

        // 2. 生成借用记录
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = sysUserMapper.selectByUsername(username).getUserId();

        TbBorrowRecord record = new TbBorrowRecord();
        record.setItemId(itemId);
        record.setStudentId(userId);
        itemMapper.insertRecord(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 必须加事务：更新记录+还库存
    public void returnItem(Long recordId, Long itemId) {
        // 1. 更新记录状态为已归还(1)
        TbBorrowRecord record = new TbBorrowRecord();
        record.setRecordId(recordId);
        record.setStatus(1);
        itemMapper.updateRecordStatus(record);

        // 2. 恢复库存
        itemMapper.increaseStock(itemId);
    }

    @Override
    public List<TbBorrowRecord> getRecordList(Long studentId) {
        return itemMapper.selectRecordList(studentId);
    }
}