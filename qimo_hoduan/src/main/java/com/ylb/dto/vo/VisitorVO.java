package com.ylb.dto.vo;

import com.ylb.entity.TbVisitor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VisitorVO extends TbVisitor {
    // 扩展字段：被访学生姓名
    private String studentName;
    private String studentNo;
    // 扩展字段：宿舍号 (方便宿管核对)
    private String roomInfo; 
}