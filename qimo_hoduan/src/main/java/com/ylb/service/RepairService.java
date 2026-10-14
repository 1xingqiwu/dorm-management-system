package com.ylb.service;

import com.ylb.entity.TbRepair;

import java.util.List;

public interface RepairService {
    void submitRepair(TbRepair repair);

    List<TbRepair> getMyRepairs(Long userId);

    List<TbRepair> getAllRepairs(Integer status);
    void finishRepair(Long repairId);
}
