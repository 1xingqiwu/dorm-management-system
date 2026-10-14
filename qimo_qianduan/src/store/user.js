import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useUserStore = defineStore('user', () => {
    const token = ref(localStorage.getItem('token') || '');
    // 0-学生, 1-辅导员, 2-宿管
    const userType = ref(parseInt(localStorage.getItem('userType')) || 0);
    const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'));

    const managedBuildings = ref(JSON.parse(localStorage.getItem('managedBuildings') || '[]'));
    function setToken(newToken) {
        token.value = newToken;
        localStorage.setItem('token', newToken);
    }

    function setBuildings(list) {
        managedBuildings.value = list;
        localStorage.setItem('managedBuildings', JSON.stringify(list));
    }

    function setUserInfo(info, type) {
        userInfo.value = info;
        userType.value = type;
        localStorage.setItem('userInfo', JSON.stringify(info));
        localStorage.setItem('userType', type);
    }

    function logout() {
        token.value = '';
        userInfo.value = {};
        managedBuildings.value = [];
        localStorage.removeItem('managedBuildings');
        localStorage.clear();
        window.location.href = '/login';
    }

    return { token, userType, userInfo, managedBuildings, setToken, setUserInfo, setBuildings, logout };
});