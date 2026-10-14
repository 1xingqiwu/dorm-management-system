package com.ylb.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TbItem {
    private Long itemId;
    private String name;           // 物品名称
    private Integer totalCount;    // 总数
    private Integer availableCount;// 当前可用数
    private String location;       // 存放位置
}