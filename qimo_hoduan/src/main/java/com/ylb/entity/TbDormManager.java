package com.ylb.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 宿管信息表实体 (tb_dorm_manager)
 */
@Data
public class TbDormManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long managerId;          // 关联 sys_user.user_id
    private String workNo;           // 工号
    private String name;             // 姓名
    private Long manageBuildingId;   // 负责管理的楼栋ID
    private String phone;            // 联系电话
}
