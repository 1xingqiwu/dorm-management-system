package com.ylb.mapper;
import com.ylb.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysConfigMapper {
    // 根据键查询值
    String selectValueByKey(@Param("key") String key);
    // 更新配置
    int updateValue(@Param("key") String key, @Param("value") String value);
}