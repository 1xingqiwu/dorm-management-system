package com.ylb.service.impl;

import com.ylb.common.exception.BizException;
import com.ylb.dto.vo.StudentProfileVO;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbElectricityLog;
import com.ylb.entity.TbRoom;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbElectricityLogMapper;
import com.ylb.mapper.TbRoomMapper;
import com.ylb.mapper.TbStudentMapper;
import com.ylb.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TbElectricityLogMapper logMapper;
    @Autowired
    private TbRoomMapper roomMapper;
    @Autowired
    private TbStudentMapper studentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    // 封装一个私有方法：获取当前登录用户的 ID
    private Long getCurrentUserId() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return sysUserMapper.selectByUsername(username).getUserId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recharge(BigDecimal amount) {
        Long userId = getCurrentUserId();
        StudentProfileVO profile = studentMapper.getProfileByUserId(userId);
        Long roomId = profile.getRoomId();

        if (roomId == null) throw new BizException("未分配宿舍");

        roomMapper.addBalance(roomId, amount);

        TbRoom room = roomMapper.selectById(roomId);

        TbElectricityLog log = new TbElectricityLog();
        log.setRoomId(roomId);
        log.setAmount(amount);
        log.setBalanceAfter(room.getElectricityBalance());
        log.setOperatorName(profile.getName());
        log.setPaymentMethod("微信支付");
        logMapper.insert(log);
    }

    @Override
    public List<TbElectricityLog> getHistory() {
        Long userId = getCurrentUserId();

        StudentProfileVO profile = studentMapper.getProfileByUserId(userId);
        Long roomId = profile.getRoomId();

        if (roomId == null) return new ArrayList<>();

        return logMapper.selectListByRoomId(roomId);
    }

    @Override
    public BigDecimal getBalance() {
        Long userId = getCurrentUserId();

        StudentProfileVO profile = studentMapper.getProfileByUserId(userId);
        Long roomId = profile.getRoomId();

        if (roomId == null) return BigDecimal.ZERO;

        TbRoom room = roomMapper.selectById(roomId);
        return room != null ? room.getElectricityBalance() : BigDecimal.ZERO;
    }
}