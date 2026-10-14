package com.ylb.controller;

import com.ylb.common.result.Result;
import com.ylb.entity.TbRoom;
import com.ylb.mapper.TbRoomMapper;
import com.ylb.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "宿舍管理")
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Operation(summary = "宿管-获取我的房间列表")
    @GetMapping("/list")
    public Result<List<TbRoom>> getMyRooms(@RequestParam(required = false) String roomNumber) {
        return Result.success(roomService.getMyRooms(roomNumber));
    }

    // 注入 Mapper
    @Autowired
    private TbRoomMapper roomMapper;

    @Operation(summary = "获取某楼栋的所有房间")
    @GetMapping("/list/{buildingId}")
    public Result<List<TbRoom>> getRoomsByBuilding(@PathVariable Long buildingId) {
        return Result.success(roomMapper.selectByBuildingId(buildingId));
    }
}