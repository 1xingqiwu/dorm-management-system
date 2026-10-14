package com.ylb.mapper;

import com.ylb.entity.TbRepair;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TbRepairMapper {
    void insert(TbRepair repair);

    List<TbRepair> selectListByStudent(Long userId);

    List<TbRepair> selectListAll(@Param("status") Integer status);
    void updateStatus(@Param("repairId") Long repairId, @Param("status") Integer status);
}
