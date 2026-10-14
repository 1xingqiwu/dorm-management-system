package com.ylb.mapper;

import com.ylb.entity.TbDormManager;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbDormManagerMapper {

    /** 新增宿管档案 */
    int insert(TbDormManager manager);
}
