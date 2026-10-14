import request from '../utils/request';

// 管理员获取开关状态
export const getRegisterConfigApi = () => request.get('/system/config/register');
// 管理员修改开关
export const updateRegisterConfigApi = (enabled) => request.post('/system/config/register', null, { params: { enabled } });