import request from '../utils/request';

// 辅导员获取卫生列表
export const getCounselorHygieneListApi = () => {
    return request({
        url: '/hygiene/counselor/list',
        method: 'get'
    });
};