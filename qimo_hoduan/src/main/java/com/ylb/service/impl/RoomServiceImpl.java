package com.ylb.service.impl;

import com.ylb.entity.TbRoom;
import com.ylb.mapper.SysUserMapper;
import com.ylb.mapper.TbRoomMapper;
import com.ylb.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private TbRoomMapper roomMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<TbRoom> getMyRooms(String roomNumber) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long managerId = sysUserMapper.selectByUsername(username).getUserId();

        return roomMapper.selectListByManagerId(managerId, roomNumber);
    }
}
