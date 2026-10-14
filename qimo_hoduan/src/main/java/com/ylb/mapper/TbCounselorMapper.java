package com.ylb.mapper;

import com.ylb.entity.TbCounselor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbCounselorMapper {

    /** 新增辅导员档案 */
    int insert(TbCounselor counselor);
}
