package com.ylb.service;

import com.ylb.entity.TbLeaveApply;
import java.util.List;

public interface LeaveService {
    void createApply(TbLeaveApply leaveApply);
    List<TbLeaveApply> getStudentList(Long userId);

    // 获取辅导员的审批列表
    List<TbLeaveApply> getCounselorList(Long counselorId, Integer status);

    // 审批操作
    void auditApply(Long applyId, Integer status, String remark);
}