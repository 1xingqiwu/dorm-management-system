package com.ylb.service.impl;

import com.ylb.dto.vo.StudentProfileVO;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbHygieneCheck;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbHygieneCheckMapper;
import com.ylb.mapper.TbStudentMapper;
import com.ylb.service.HygieneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HygieneServiceImpl implements HygieneService {

    @Autowired
    private TbHygieneCheckMapper hygieneMapper;
    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    // 获取当前登录用户名
    private String getCurrentUsername() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCheck(TbHygieneCheck check) {
        // 自动填入检查日期为今天
        check.setCheckDate(LocalDate.now());
        
        // 自动填入检查人姓名 (当前登录的宿管)
        SysUser manager = sysUserMapper.selectByUsername(getCurrentUsername());
        check.setCheckerName(manager.getNickname());
        
        hygieneMapper.insert(check);
    }

    @Override
    public List<TbHygieneCheck> getMyHistory() {
        // 1. 找当前学生
        SysUser user = sysUserMapper.selectByUsername(getCurrentUsername());
        // 2. 找学生详情拿到 roomId
        StudentProfileVO profile = studentMapper.getProfileByUserId(user.getUserId());
        Long roomId = profile.getRoomId();
        
        if (roomId == null) {
            return new ArrayList<>(); // 未分配宿舍，无记录
        }
        
        // 3. 查记录
        return hygieneMapper.selectListByRoomId(roomId);
    }

    @Override
    public List<TbHygieneCheck> getCounselorList() {
        // 1. 获取当前登录的辅导员信息
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);

        // 注意：sys_user 的 user_id 就是 counselor_id
        Long counselorId = user.getUserId();

        // 2. 查询
        return hygieneMapper.selectListByCounselorId(counselorId);
    }
}