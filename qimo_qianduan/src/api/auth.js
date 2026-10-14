import request from '../utils/request';

// 登录
export const loginApi = (data) => {
    return request({
        url: '/auth/login',
        method: 'post',
        data
    });
};

// 获取用户信息
export const getUserInfoApi = () => {
    return request({
        url: '/auth/info',
        method: 'get'
    });
};

// 修改资料
export const updateProfileApi = (data) => {
    return request({
        url: '/auth/update/profile',
        method: 'post',
        data
    });
};

// 修改密码
export const updatePasswordApi = (data) => {
    return request({
        url: '/auth/update/password',
        method: 'post',
        data
    });
};

// 发送验证码
export const sendResetCodeApi = (username, email) => {
    return request({
        url: '/auth/send-code',
        method: 'post',
        params: { username, email }
    });
};

// 重置密码
export const resetPasswordByEmailApi = (data) => {
    return request({
        url: '/auth/reset-password',
        method: 'post',
        data
    });
};

// 公开获取注册状态
export const getPublicRegisterStatusApi = () => request.get('/auth/register-status');
// 注册接口
export const registerApi = (data) => request.post('/auth/register', data);