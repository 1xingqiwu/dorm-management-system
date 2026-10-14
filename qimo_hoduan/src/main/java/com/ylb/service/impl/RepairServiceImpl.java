package com.ylb.service.impl;

import com.ylb.common.exception.BizException;
import com.ylb.dto.vo.StudentProfileVO;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbRepair;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbRepairMapper;
import com.ylb.mapper.TbStudentMapper;
import com.ylb.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private TbRepairMapper repairMapper;
    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void submitRepair(TbRepair repair) {
        // 1. 获取当前学生信息
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);
        
        // 2. 查学生详情，获取宿舍信息 (因为报修要关联楼栋和房间)
        StudentProfileVO profile = studentMapper.getProfileByUserId(user.getUserId());
        
        // 3. 补全信息
        repair.setStudentId(user.getUserId());
        // 从学生档案读取真实楼栋/房间ID（不再写死，避免所有报修挂到同一房间）
        if (profile == null || profile.getBuildingId() == null || profile.getRoomId() == null) {
            throw new BizException("未找到该学生的宿舍信息，无法提交报修");
        }
        repair.setBuildingId(profile.getBuildingId());
        repair.setRoomId(profile.getRoomId());
        
        repairMapper.insert(repair);
    }

    @Override
    public List<TbRepair> getMyRepairs(Long userId) {
        return repairMapper.selectListByStudent(userId);
    }

    @Override
    public List<TbRepair> getAllRepairs(Integer status) {
        return repairMapper.selectListAll(status);
    }

    @Override
    public void finishRepair(Long repairId) {
        // 状态改为 1 (已完成)
        repairMapper.updateStatus(repairId, 1);
    }
}