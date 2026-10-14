package com.ylb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal; // 金额必须用 BigDecimal，不能用 Double
import java.time.LocalDateTime;

@Data
public class TbElectricityLog {
    private Long logId;
    private Long roomId;
    private BigDecimal amount;       // 充值金额
    private BigDecimal balanceAfter; // 充值后余额 (快照)
    private String operatorName;     // 充值人
    private String paymentMethod;    // 微信/支付宝
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}