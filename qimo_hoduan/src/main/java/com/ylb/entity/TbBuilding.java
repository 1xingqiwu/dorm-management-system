package com.ylb.entity;

import lombok.Data;

@Data
public class TbBuilding {
    private Long buildingId;
    private String name;        // 楼栋名
    private Integer type;       // 1-男宿, 2-女宿
    private Integer totalFloor; // 总层数
    private String location;    // 位置
    private String description; // 描述
}