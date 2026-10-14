package com.ylb.service;

import com.ylb.entity.TbHygieneCheck;
import java.util.List;

public interface HygieneService {
    // 宿管录入
    void addCheck(TbHygieneCheck check);
    
    // 学生查看自己的
    List<TbHygieneCheck> getMyHistory();

    List<TbHygieneCheck> getCounselorList();
}