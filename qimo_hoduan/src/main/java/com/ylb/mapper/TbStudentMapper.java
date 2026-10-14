package com.ylb.mapper;

import com.ylb.dto.vo.StudentProfileVO;
import com.ylb.entity.TbStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TbStudentMapper {
    // 根据用户ID查询详细信息
    StudentProfileVO getProfileByUserId(@Param("userId") Long userId);

    void updateEmergencyInfo(@Param("studentId") Long studentId,
                             @Param("contact") String contact,
                             @Param("phone") String phone);

    List<StudentProfileVO> selectListByCounselor(@Param("counselorId") Long counselorId,
                                                 @Param("name") String name,
                                                 @Param("studentNo") String studentNo,
                                                 @Param("className") String className);

    // 注意：这里复用 StudentProfileVO 即可，它包含了我们需要展示的信息
    List<StudentProfileVO> selectByRoomId(@Param("roomId") Long roomId);

    List<StudentProfileVO> selectRoommates(@Param("roomId") Long roomId, @Param("myId") Long myId);


    void insert(TbStudent student);

    /** 管理员新增账号时同步建档(仅写必填列，性别/学籍状态走数据库默认值) */
    int insertByAdmin(TbStudent student);
}