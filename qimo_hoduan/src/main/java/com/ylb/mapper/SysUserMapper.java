package com.ylb.mapper;

import com.ylb.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {
    
    // 根据用户名查询（登录用）
    SysUser selectByUsername(@Param("username") String username);

    // 根据ID查询
    SysUser selectById(@Param("userId") Long userId);

    // 新增用户
    int insert(SysUser user);

    // 更新用户
    int update(SysUser user);
    
    // 逻辑删除
    int deleteById(@Param("userId") Long userId);

    int updateById(SysUser user);

    // 添加查询列表方法
    List<SysUser> selectList(@Param("username") String username, @Param("userType") Integer userType);
}