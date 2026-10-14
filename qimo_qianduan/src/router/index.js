import { createRouter, createWebHistory } from 'vue-router';
import { useUserStore } from '../store/user';
// 引入 Layout
import Layout from '../layout/index.vue';
import { ElMessage } from 'element-plus';

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/login/index.vue'),
        meta: { title: '登录' }
    },
    {
        path: '/',
        component: Layout,
        children: [
            // 4. 超级管理员路由
            {
                path: 'admin/settings',
                name: 'AdminSettings',
                component: () => import('../views/admin/settings.vue'),
                meta: { title: '系统设置', role: 3 }
            },
            {
                path: 'admin/user',
                name: 'AdminUserManage',
                component: () => import('../views/admin/user-manage.vue'),
                meta: { title: '账号管理', role: 3 }
            },
            // 个人中心
            {
                path: '/profile', // 注意这里加了斜杠，代表绝对路径
                name: 'UserProfile',
                component: () => import('../views/profile/index.vue'),
                meta: { title: '个人中心' } // 不加 role 限制，所有人都能访问
            },
            // 1. 学生路由
            {
                path: 'student/dashboard',
                name: 'StudentAi',
                component: () => import('../views/ai/index.vue'),
                meta: { title: 'AI助手', role: 0 }
            },
            {
                path: 'student/profile',
                name: 'StudentProfile',
                component: () => import('../views/student/profile.vue'),
                meta: { title: '个人信息', role: 0 }
            },
            {
                path: 'student/leave',
                name: 'StudentLeave',
                component: () => import('../views/student/leave.vue'),
                meta: { title: '请假申请', role: 0 }
            },
            {
                path: 'student/repair',
                name: 'StudentRepair',
                component: () => import('../views/student/repair.vue'),
                meta: { title: '维修报修', role: 0 }
            },
            {
                path: 'student/payment',
                name: 'StudentPayment',
                component: () => import('../views/student/payment.vue'),
                meta: { title: '电费充值', role: 0 }
            },
            {
                path: 'student/borrow',
                name: 'StudentBorrow',
                component: () => import('../views/student/borrow.vue'),
                meta: { title: '物品借用', role: 0 }
            },
            {
                path: 'student/notice',
                name: 'StudentNotice',
                component: () => import('../views/student/notice.vue'),
                meta: { title: '通知公告', role: 0 }
            },

            // 2. 辅导员路由
            {
                path: 'counselor/dashboard',
                name: 'CounselorDashboard',
                component: () => import('../views/dashboard/counselor.vue'),
                meta: { title: '工作台', role: 1 }
            },
            {
                path: 'counselor/leave-audit',
                name: 'CounselorLeaveAudit',
                component: () => import('../views/counselor/leave-audit.vue'),
                meta: { title: '请假审批', role: 1 }
            },
            {
                path: 'counselor/students',
                name: 'CounselorStudents',
                component: () => import('../views/counselor/students.vue'),
                meta: { title: '学生管理', role: 1 }
            },
            {
                path: 'student/hygiene',
                name: 'StudentHygiene',
                component: () => import('../views/student/hygiene.vue'),
                meta: { title: '卫生检查', role: 0 }
            },
            {
                path: 'counselor/hygiene',
                name: 'CounselorHygiene',
                component: () => import('../views/counselor/hygiene.vue'),
                meta: { title: '卫生统计', role: 1 }
            },

            // 3. 宿管路由
            {
                path: 'manager/building/:id',
                name: 'ManagerBuildingDetail',
                component: () => import('../views/manager/building-detail.vue'),
                meta: { title: '楼栋详情', role: 2 }
            },
            {
                path: 'manager/repair-audit', // 这里路径名字可以叫 repair-process 更好，看你Sidebar里写的啥
                name: 'ManagerRepair',
                component: () => import('../views/manager/repair-process.vue'),
                meta: { title: '报修处理', role: 2 }
            },
            {
                path: 'manager/visitor',
                name: 'ManagerVisitor',
                component: () => import('../views/manager/visitor.vue'),
                meta: { title: '访客管理', role: 2 }
            },
            {
                path: 'manager/notice',
                name: 'ManagerNotice',
                component: () => import('../views/manager/notice.vue'),
                meta: { title: '公告管理', role: 2 }
            },
            {
                path: 'manager/item',
                name: 'ManagerItem',
                component: () => import('../views/manager/item.vue'),
                meta: { title: '物品管理', role: 2 }
            },
            {
                path: 'manager/hygiene',
                name: 'ManagerHygiene',
                component: () => import('../views/manager/hygiene.vue'),
                meta: { title: '卫生检查', role: 2 }
            },
        ]
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

// 路由守卫
router.beforeEach((to, from, next) => {
    const userStore = useUserStore();
    const token = userStore.token;
    const userType = userStore.userType;

    // 1. 如果去登录页，直接放行
    if (to.path === '/login') {
        next();
        return;
    }

    // 2. 如果没有 Token，强制跳登录
    if (!token) {
        next('/login');
        return;
    }

    // 3. 处理根路径 '/' 的跳转
    if (to.path === '/') {
        if (userType === 0) next('/student/dashboard');
        else if (userType === 1) next('/counselor/dashboard');
        else if (userType === 2) next('/manager/repair-audit'); // 宿管
        else if (userType === 3) next('/admin/settings');       // 管理员
        return;
    }

    // 4. 权限拦截逻辑 (修复版)
    if (to.meta.role !== undefined && to.meta.role !== userType) {

        // 🟢 特殊放行：超级管理员(3) 允许访问 宿管(2) 的页面 (例如公告管理)
        if (userType === 3 && to.meta.role === 2) {
            next();
            return;
        }

        // 确实无权访问
        ElMessage.error('无权访问');

        // 根据身份踢回对应的首页
        if (userType === 3) next('/admin/settings');
        else if (userType === 2) next('/manager/repair-audit');
        else if (userType === 1) next('/counselor/dashboard');
        else next('/student/dashboard');
        return;
    }

    next();
});

export default router;