package com.ylb.service;

import com.ylb.entity.TbElectricityLog;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    // 充值
    @Transactional(rollbackFor = Exception.class) // 加事务，保证扣钱和记录一致
    void recharge(BigDecimal amount);

    // 获取记录
    List<TbElectricityLog> getHistory();

    // 获取余额
    BigDecimal getBalance();
}
