import request from '../utils/request';

export const getStudentProfileApi = () => {
    return request({
        url: '/student/profile',
        method: 'get'
    });
};

export const updateProfileApi = (data) => {
    return request({
        url: '/student/profile',
        method: 'post',
        data
    });
};

// 获取我的舍友列表
export const getMyRoommatesApi = () => {
    return request({
        url: '/student/roommates',
        method: 'get'
    });
};