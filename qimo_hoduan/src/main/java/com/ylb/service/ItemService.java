package com.ylb.service;

import com.ylb.entity.TbBorrowRecord;
import com.ylb.entity.TbItem;
import java.util.List;

public interface ItemService {
    // 物品管理
    List<TbItem> getItemList();
    void addItem(TbItem item);
    void updateItem(TbItem item);
    void deleteItem(Long itemId);

    // 借用业务
    void borrowItem(Long itemId); // 学生借用
    void returnItem(Long recordId, Long itemId); // 归还
    List<TbBorrowRecord> getRecordList(Long studentId); // 查记录
}