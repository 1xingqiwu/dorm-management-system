package com.ylb.service;

import com.ylb.dto.StudentUpdateDTO;
import com.ylb.dto.vo.StudentProfileVO;

import java.util.List;

public interface StudentService {
    StudentProfileVO getProfile();

    void updateProfile(StudentUpdateDTO dto);

    List<StudentProfileVO> getMyStudents(String name, String studentNo, String className);

    List<StudentProfileVO> getMyRoommates();
}