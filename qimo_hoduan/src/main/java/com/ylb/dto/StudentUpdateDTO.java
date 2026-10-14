package com.ylb.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentUpdateDTO {
    @Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;             // 个人手机号

    @Size(max = 64, message = "紧急联系人长度不能超过64位")
    private String emergencyContact;  // 紧急联系人

    @Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "紧急联系电话格式不正确")
    private String emergencyPhone;    // 紧急联系电话
}
