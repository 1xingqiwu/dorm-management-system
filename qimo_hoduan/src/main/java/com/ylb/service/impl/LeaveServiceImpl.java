package com.ylb.service.impl;

import com.ylb.common.exception.BizException;
import com.ylb.dto.vo.StudentProfileVO;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbLeaveApply;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbLeaveApplyMapper;
import com.ylb.mapper.TbStudentMapper;
import com.ylb.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private TbLeaveApplyMapper leaveMapper;
    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void createApply(TbLeaveApply leave) {
        // 1. 获取当前登录学生 ID
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);
        
        // 2. 补全学生ID
        leave.setStudentId(user.getUserId());
        
        // 3. 从学生档案读取真实辅导员ID（不再写死，避免所有人的请假挂到同一辅导员）
        StudentProfileVO profile = studentMapper.getProfileByUserId(user.getUserId());
        if (profile == null || profile.getCounselorId() == null) {
            throw new BizException("未找到该学生的辅导员信息，无法提交请假申请");
        }
        leave.setCounselorId(profile.getCounselorId());
        
        // 4. 插入数据库
        leaveMapper.insert(leave);
    }

    @Override
    public List<TbLeaveApply> getStudentList(Long userId) {
        // 只查询自己的请假记录
        return leaveMapper.selectList(userId, null, null);
    }

    @Override
    public List<TbLeaveApply> getCounselorList(Long counselorId, Integer status) {
        // 调用之前写好的 Mapper (支持按 counselorId 和 status 筛选)
        return leaveMapper.selectList(null, counselorId, status);
    }

    @Override
    public void auditApply(Long applyId, Integer status, String remark) {
        TbLeaveApply apply = new TbLeaveApply();
        apply.setApplyId(applyId);
        apply.setStatus(status);
        apply.setAuditRemark(remark);
        // 这里其实还应该记录 audit_time，建议去 Entity 加个字段或复用 updateTime
         apply.setAuditTime(LocalDateTime.now());

        // MyBatis-Plus 有 updateById，原生 MyBatis 需要手写 updateSelective
        // 这里我们需要去 Mapper XML 补一个 update 方法
        leaveMapper.updateAudit(apply);
    }
}