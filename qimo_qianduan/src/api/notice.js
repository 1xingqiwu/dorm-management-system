import request from '../utils/request';

// 获取公告列表 (复用后端的 list 接口)
export const getNoticeListApi = (title) => {
    return request({
        url: '/notice/list',
        method: 'get',
        params: { title }
    });
};