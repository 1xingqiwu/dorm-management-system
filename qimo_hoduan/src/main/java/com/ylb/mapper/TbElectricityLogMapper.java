package com.ylb.mapper;

import com.ylb.entity.TbElectricityLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TbElectricityLogMapper {
    // 插入日志
    int insert(TbElectricityLog log);
    // 查询某个房间的充值记录
    List<TbElectricityLog> selectListByRoomId(@Param("roomId") Long roomId);


}