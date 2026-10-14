package com.ylb.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TbRoom {
    private Long roomId;
    private Long buildingId;
    private Integer floorNumber;
    private String roomNumber;
    private String category;       // 4人间/6人间
    private Integer capacity;      // 总床位
    private Integer occupied;      // 已住人数
    private BigDecimal electricityBalance; // 电费余额
    private BigDecimal waterBalance;       // 水费余额
    private Integer lastCleanScore;
    private LocalDateTime createTime;
}