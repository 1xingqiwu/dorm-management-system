package com.ylb.dto.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StudentProfileVO {
    // 基础信息
    private String studentNo;      // 学号
    private String name;           // 姓名
    private String gender;         // 性别 (男/女)
    private String collegeName;    // 学院
    private String majorName;      // 专业
    private String className;      // 班级
    private String phone;          // 手机号 (从 sys_user 表查)
    
    // 宿舍信息 (需要关联查询)
    private String buildingName;   // 楼栋名 (如：南苑1号楼)
    private String roomNumber;     // 宿舍号 (如：101)
    private Integer bedNumber;     // 床位号
    
    // 辅导员信息
    private String counselorName;  // 辅导员姓名
    private String counselorPhone; // 辅导员电话

    private String status;         // 状态 (在校)

    private String emergencyContact;    // 紧急联系人
    private String emergencyPhone;    // 紧急联系人电话

    private Long roomId;
    private Long buildingId;      // 楼栋ID (tb_student.building_id)
    private Long counselorId;     // 辅导员ID (tb_student.counselor_id)
    private Long studentId;
}