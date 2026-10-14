package com.ylb.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 辅导员信息表实体 (tb_counselor)
 */
@Data
public class TbCounselor implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long counselorId;     // 关联 sys_user.user_id
    private String workNo;        // 工号
    private String name;          // 姓名
    private String collegeName;   // 负责院系
    private String phone;         // 联系电话
    private LocalDateTime createTime;
}
