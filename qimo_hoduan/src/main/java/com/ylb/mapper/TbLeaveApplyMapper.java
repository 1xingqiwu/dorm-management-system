package com.ylb.mapper;

import com.ylb.entity.TbLeaveApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface TbLeaveApplyMapper {
    // 新增申请
    int insert(TbLeaveApply leaveApply);

    // 查询列表 (支持按学生查，或按辅导员查)
    List<TbLeaveApply> selectList(@Param("studentId") Long studentId, 
                                  @Param("counselorId") Long counselorId, 
                                  @Param("status") Integer status);

    void updateAudit(TbLeaveApply apply);
}