package com.ylb.mapper;

import com.ylb.entity.TbHygieneCheck;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TbHygieneCheckMapper {
    // 录入检查
    int insert(TbHygieneCheck check);
    
    // 查询某房间的历史记录
    List<TbHygieneCheck> selectListByRoomId(@Param("roomId") Long roomId);

    List<TbHygieneCheck> selectListByCounselorId(@Param("counselorId") Long counselorId);
}