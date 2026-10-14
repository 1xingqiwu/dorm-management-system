package com.ylb.mapper;

import com.ylb.entity.TbBorrowRecord;
import com.ylb.entity.TbItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TbItemMapper {
    // 1. 物品相关
    List<TbItem> selectItemList();
    int insertItem(TbItem item);
    int updateItem(TbItem item);
    int deleteItem(Long itemId);
    
    // 扣减库存 (-1)
    int decreaseStock(Long itemId);
    // 恢复库存 (+1)
    int increaseStock(Long itemId);

    // 2. 借用记录相关
    int insertRecord(TbBorrowRecord record);
    int updateRecordStatus(TbBorrowRecord record);
    
    // 查询记录 (支持按学生查，或查所有)
    List<TbBorrowRecord> selectRecordList(@Param("studentId") Long studentId);
}