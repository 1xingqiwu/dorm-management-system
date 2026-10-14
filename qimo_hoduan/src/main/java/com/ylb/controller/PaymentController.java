package com.ylb.controller;

import com.ylb.common.exception.BizException;
import com.ylb.common.result.Result;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbElectricityLog;
import com.ylb.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "电费管理")
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "电费充值")
    @PostMapping("/recharge")
    public Result<String> recharge(@RequestBody Map<String, BigDecimal> params) {
        // 前端传 { "amount": 100.00 }
        BigDecimal amount = params.get("amount");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BizException("充值金额必须大于0");
        }
        paymentService.recharge(amount);
        return Result.success("充值成功");
    }


    @Operation(summary = "获取余额和记录")
    @GetMapping("/info")
    public Result<Map<String, Object>> getInfo() {
        // 1. 获取余额
        BigDecimal balance = paymentService.getBalance();

        // 2. 获取记录
        List<TbElectricityLog> logs = paymentService.getHistory();

        // 3. 组装返回
        Map<String, Object> map = new HashMap<>();
        map.put("balance", balance);
        map.put("logs", logs);

        return Result.success(map);
    }
}