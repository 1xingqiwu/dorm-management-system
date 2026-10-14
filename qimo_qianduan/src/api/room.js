import request from '../utils/request';

// 获取某楼栋的房间
export const getRoomsByBuildingApi = (buildingId) => {
    return request({
        url: `/room/list/${buildingId}`,
        method: 'get'
    });
};

// 获取某房间的学生
export const getStudentsByRoomApi = (roomId) => {
    return request({
        url: `/student/list/room/${roomId}`,
        method: 'get'
    });
};