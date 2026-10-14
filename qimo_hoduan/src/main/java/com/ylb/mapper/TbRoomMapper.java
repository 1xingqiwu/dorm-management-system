package com.ylb.mapper;

import com.ylb.entity.TbRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TbRoomMapper {
    
    // 根据ID查询房间详情（用于查余额）
    TbRoom selectById(@Param("roomId") Long roomId);

    // 充值（增加余额）
    int addBalance(@Param("roomId") Long roomId, @Param("amount") BigDecimal amount);


    List<TbRoom> selectListByManagerId(@Param("managerId") Long managerId, @Param("roomNumber") String roomNumber);

    List<TbRoom> selectByBuildingId(@Param("buildingId") Long buildingId);
}