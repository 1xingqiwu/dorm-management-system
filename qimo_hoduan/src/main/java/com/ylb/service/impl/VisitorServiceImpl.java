package com.ylb.service.impl;

import com.ylb.dto.vo.VisitorVO;
import com.ylb.entity.TbVisitor;
import com.ylb.mapper.TbVisitorMapper;
import com.ylb.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private TbVisitorMapper visitorMapper;

    @Override
    public List<VisitorVO> getList(String name, Integer status) {
        return visitorMapper.selectList(name, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditVisitor(Long visitorId, Integer status) {
        TbVisitor updateInfo = new TbVisitor();
        updateInfo.setVisitorId(visitorId);
        updateInfo.setStatus(status);

        // 如果是确认离开(3)，自动填充离开时间
        if (status == 3) {
            updateInfo.setVisitEndTime(LocalDateTime.now());
        }

        visitorMapper.update(updateInfo);
    }
}