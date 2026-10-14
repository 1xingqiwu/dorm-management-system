package com.ylb.mapper;

import com.ylb.entity.TbBuilding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TbBuildingMapper {
    List<TbBuilding> selectByManagerId(@Param("managerId") Long managerId);
    
    // 根据ID查详情
    TbBuilding selectById(@Param("buildingId") Long buildingId);
}