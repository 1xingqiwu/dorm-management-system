package com.ylb.service.impl;

import com.ylb.dto.StudentUpdateDTO;
import com.ylb.dto.vo.StudentProfileVO;
import com.ylb.entity.SysUser;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbStudentMapper;
import com.ylb.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private TbStudentMapper studentMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public StudentProfileVO getProfile() {
        // 1. 获取当前登录用户的 ID
        // SecurityContextHolder... 这里为了简便，假设你有个工具类 SecurityUtils.getUserId()
        // 或者直接像下面这样写：
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 这里有个小问题：我们需要 userId，但 SecurityContext 里存的是 username。
        // 建议：在 LoginUser 里存 id，或者先查一下 sys_user。
        // 为了快速实现，我们复用之前的 SysUserMapper

        // *更优雅的做法是在 JwtAuthenticationTokenFilter 里就把 userId 存进 Authentication*
        // *但现在我们先用 username 查一次*
        Long userId = sysUserMapper.selectByUsername(username).getUserId();

        return studentMapper.getProfileByUserId(userId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class) // 开启事务，保证两张表同时更新成功
    public void updateProfile(StudentUpdateDTO dto) {
        // 1. 获取当前用户ID
        // (同样建议封装一个 SecurityUtils，这里先为了方便写死或从 Context 取)
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);

        // 2. 更新 sys_user 表的手机号
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
            sysUserMapper.update(user); // 需确保 Mapper 有 update 方法
        }

        // 3. 更新 tb_student 表的紧急联系人
        // 假设你还没有 Student 的 Mapper update，我们需要加一个
        // 这里用 MyBatis-Plus 会方便很多，原生 MyBatis 需要手写 SQL
        studentMapper.updateEmergencyInfo(user.getUserId(), dto.getEmergencyContact(), dto.getEmergencyPhone());
    }

    @Override
    public List<StudentProfileVO> getMyStudents(String name, String studentNo, String className) {
        // 1. 获取当前辅导员ID (sys_user 的 ID 就是 counselor_id)
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long counselorId = sysUserMapper.selectByUsername(username).getUserId();

        // 2. 查询
        return studentMapper.selectListByCounselor(counselorId, name, studentNo, className);
    }

    @Override
    public List<StudentProfileVO> getMyRoommates() {
        // 1. 获取当前用户
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);

        // 2. 获取自己的 roomId
        StudentProfileVO myProfile = studentMapper.getProfileByUserId(user.getUserId());
        Long roomId = myProfile.getRoomId();

        if (roomId == null) return new ArrayList<>();

        // 3. 查询室友
        return studentMapper.selectRoommates(roomId, user.getUserId());
    }
}