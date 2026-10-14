package com.ylb.dto.vo;

import com.ylb.entity.TbLeaveApply;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true) // 继承父类的字段
public class LeaveApplyVO extends TbLeaveApply {
    // 新增两个展示用的字段
    private String studentName;
    private String studentNo;
}