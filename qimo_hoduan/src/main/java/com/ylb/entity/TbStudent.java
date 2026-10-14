package com.ylb.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TbStudent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long studentId;       // 关联 sys_user.user_id
    private String studentNo;     // 学号
    private String name;          // 姓名
    private Integer gender;       // 1-男
    private String collegeName;   // 院系
    private String majorName;     // 专业
    private String className;     // 班级
    private Integer enrollmentYear;
    
    // 关键关联字段
    private Long counselorId;     // 辅导员ID
    private Long buildingId;      // 楼栋ID
    private Long roomId;          // 房间ID
    private Integer bedNumber;    // 床位号
    
    private String emergencyContact;
    private String emergencyPhone;
    private Integer status;       // 1-在校
    
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}