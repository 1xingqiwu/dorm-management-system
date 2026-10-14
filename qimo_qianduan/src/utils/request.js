import axios from 'axios';
import { ElMessage } from 'element-plus';
import { useUserStore } from '../store/user'; // 稍后创建

const service = axios.create({
    baseURL: '/api', // 配合 vite.config.js 代理
    timeout: 5000
});

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        const userStore = useUserStore();
        if (userStore.token) {
            config.headers.Authorization = `Bearer ${userStore.token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (response) => {
        const { code, msg, data } = response.data;

        // 二进制文件直接返回
        if (response.config.responseType === 'blob') {
            return response;
        }

        if (code === 200) {
            return data;
        } else {
            ElMessage.error(msg || '系统错误');
            return Promise.reject(new Error(msg || 'Error'));
        }
    },
    (error) => {
        let message = '';
        const status = error.response?.status;
        switch (status) {
            case 401:
                message = 'Token已过期，请重新登录';
                const userStore = useUserStore();
                userStore.logout();
                break;
            case 403:
                message = '无权访问';
                break;
            case 500:
                message = '服务器内部错误';
                break;
            default:
                message = '网络连接失败';
        }
        ElMessage.error(message);
        return Promise.reject(error);
    }
);

export default service;