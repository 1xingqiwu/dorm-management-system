package com.ylb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 新增物品请求参数（镜像 TbItem 实体字段，保证前端 JSON 原样绑定）
 */
@Data
public class ItemAddDTO {
    private Long itemId;

    @NotBlank(message = "物品名称不能为空")
    private String name;           // 物品名称

    @NotNull(message = "库存总数不能为空")
    private Integer totalCount;    // 总数

    private Integer availableCount;// 当前可用数

    private String location;       // 存放位置
}
