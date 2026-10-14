import request from '../utils/request';

// 充值接口
export const rechargeApi = (amount) => {
    return request({
        url: '/payment/recharge',
        method: 'post',
        data: { amount } // 传 JSON { "amount": 50 }
    });
};

// 获取余额和记录 (假设你后端写了这个接口，如果没有，我们马上补)
export const getPaymentInfoApi = () => {
    return request({
        url: '/payment/info',
        method: 'get'
    });
};