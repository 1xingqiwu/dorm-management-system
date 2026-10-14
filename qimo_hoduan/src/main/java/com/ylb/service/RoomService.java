package com.ylb.service;

import com.ylb.entity.TbRoom;

import java.util.List;

public interface RoomService {

    List<TbRoom> getMyRooms(String roomNumber);
}
