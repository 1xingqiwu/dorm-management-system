package com.ylb.mapper;

import com.ylb.dto.vo.VisitorVO;
import com.ylb.entity.TbVisitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;


@Mapper
public interface TbVisitorMapper {
    List<VisitorVO> selectList(@Param("name") String name, @Param("status") Integer status);

    int update(TbVisitor visitor);
                      
    int insert(TbVisitor visitor);
}