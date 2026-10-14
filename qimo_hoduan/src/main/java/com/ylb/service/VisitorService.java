package com.ylb.service;

import com.ylb.dto.vo.VisitorVO;
import java.util.List;

public interface VisitorService {
    // 获取列表
    List<VisitorVO> getList(String name, Integer status);
    
    // 审核/处理 (status: 1通过, 3离开, 4驳回)
    void auditVisitor(Long visitorId, Integer status);
}