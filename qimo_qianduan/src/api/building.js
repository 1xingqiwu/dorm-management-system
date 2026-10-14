import request from '../utils/request';

// 获取我管理的楼栋列表
export const getMyBuildingsApi = () => {
    return request({
        url: '/building/my',
        method: 'get'
    });
};

// 获取楼栋详情
export const getBuildingDetailApi = (id) => {
    return request({
        url: `/building/${id}`,
        method: 'get'
    });
};