package com.ylb.mapper;

import com.ylb.entity.SysNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysNoticeMapper {
    // 查询列表 (支持标题模糊搜素)
    List<SysNotice> selectList(@Param("title") String title);
    
    int insert(SysNotice notice);
    
    int update(SysNotice notice);
    
    // 逻辑删除
    int deleteById(@Param("noticeId") Long noticeId);
}