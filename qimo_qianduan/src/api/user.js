import request from '../utils/request';

export const createUserApi = (data) => request.post('/user/create', data);
export const getUserListApi = (params) => request.get('/user/list', {params});
export const updateUserApi = (data) => request.post('/user/update', data);
export const changeUserStatusApi = (userId, status) => request.post('/user/status', null, {params: {userId, status}});
export const deleteUserApi = (id) => request.post(`/user/delete/${id}`);
export const resetUserPwdApi = (userId, password) => {
    return request.post('/user/reset-pwd', null, {
        params: {userId, password}
    });
};