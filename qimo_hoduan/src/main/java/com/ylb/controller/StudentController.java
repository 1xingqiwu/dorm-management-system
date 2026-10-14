package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.dto.StudentUpdateDTO;
import com.ylb.dto.vo.StudentProfileVO;
import com.ylb.mapper.TbStudentMapper;
import com.ylb.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "学生端接口")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Operation(summary = "获取个人详细资料")
    @GetMapping("/profile")
    public Result<StudentProfileVO> getProfile() {
        StudentProfileVO vo = studentService.getProfile();
        return Result.success(vo);
    }

    @Operation(summary = "更新个人信息")
    @PostMapping("/profile")
    public Result<String> updateProfile(@Valid @RequestBody StudentUpdateDTO dto) {
        studentService.updateProfile(dto);
        return Result.success("修改成功");
    }

    @Operation(summary = "辅导员-获取我的学生")
    @GetMapping("/list/my")
    public Result<List<StudentProfileVO>> getMyStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String studentNo,
            @RequestParam(required = false) String className) {
        return Result.success(studentService.getMyStudents(name, studentNo, className));
    }

    @Autowired
    private TbStudentMapper studentMapper;

    @Operation(summary = "获取某房间的学生")
    @GetMapping("/list/room/{roomId}")
    public Result<List<StudentProfileVO>> getStudentsByRoom(@PathVariable Long roomId) {
        return Result.success(studentMapper.selectByRoomId(roomId));
    }

    @Operation(summary = "获取我的舍友")
    @GetMapping("/roommates")
    public Result<List<StudentProfileVO>> getMyRoommates() {
        return Result.success(studentService.getMyRoommates());
    }
}