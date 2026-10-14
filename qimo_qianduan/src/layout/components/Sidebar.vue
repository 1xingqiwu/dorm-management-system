<template>
  <el-aside width="220px" class="sidebar-container">
    <div class="logo">
      <span>智慧宿管系统</span>
    </div>

    <el-menu
        active-text-color="var(--color-primary)"
        background-color="var(--color-sidebar-bg)"
        text-color="var(--color-sidebar-hover-text)"
        :default-active="route.path"
        router
    >
      <!-- 1. 学生菜单 -->
      <template v-if="userStore.userType === 0">
        <el-menu-item index="/student/dashboard">
          <el-icon><service /></el-icon>
          <span>AI 助手</span>
        </el-menu-item>
        <el-menu-item index="/student/profile">
          <el-icon><User /></el-icon>
          <span>个人信息</span>
        </el-menu-item>
        <el-menu-item index="/student/repair">
          <el-icon><Tools /></el-icon>
          <span>维修报修</span>
        </el-menu-item>
        <el-menu-item index="/student/leave">
          <el-icon><Position /></el-icon>
          <span>请假申请</span>
        </el-menu-item>
        <el-menu-item index="/student/payment">
          <el-icon><Money /></el-icon>
          <span>电费充值</span>
        </el-menu-item>
        <el-menu-item index="/student/hygiene">
          <el-icon><ToiletPaper /></el-icon> <!-- 或者用 List 图标 -->
          <span>卫生检查</span>
        </el-menu-item>
        <el-menu-item index="/student/notice">
          <el-icon><Bell /></el-icon>
          <span>通知公告</span>
        </el-menu-item>
        <el-menu-item index="/student/borrow">
          <el-icon><Box /></el-icon> <!-- 记得引入 Box 图标 -->
          <span>物品借用</span>
        </el-menu-item>
      </template>

      <!-- 2. 辅导员菜单 -->
      <template v-if="userStore.userType === 1">
        <el-menu-item index="/counselor/dashboard">
          <el-icon><DataBoard /></el-icon>
          <span>工作台</span>
        </el-menu-item>
        <el-menu-item index="/counselor/students">
          <el-icon><UserFilled /></el-icon>
          <span>学生管理</span>
        </el-menu-item>
        <el-menu-item index="/counselor/leave-audit">
          <el-icon><Stamp /></el-icon>
          <span>请假审批</span>
        </el-menu-item>
        <el-menu-item index="/counselor/hygiene">
          <el-icon><List /></el-icon>
          <span>卫生统计</span>
        </el-menu-item>

      </template>

      <!-- 3. 宿管菜单 -->
      <template v-if="userStore.userType === 2">
        <el-sub-menu index="building-manage">
          <template #title>
            <el-icon><OfficeBuilding /></el-icon>
            <span>楼栋管理</span>
          </template>

          <!-- 循环渲染楼栋 -->
          <el-menu-item
              v-for="item in userStore.managedBuildings"
              :key="item.buildingId"
              :index="'/manager/building/' + item.buildingId"
          >
            {{ item.name }}
          </el-menu-item>
        </el-sub-menu>

        <el-menu-item index="/manager/repair-audit">
          <el-icon><Tools /></el-icon>
          <span>报修处理</span>
        </el-menu-item>
        <el-menu-item index="/manager/visitor">
          <el-icon><User /></el-icon>
          <span>访客管理</span>
        </el-menu-item>
        <el-menu-item index="/manager/notice">
          <el-icon><Bell /></el-icon>
          <span>公告管理</span>
        </el-menu-item>
        <el-menu-item index="/manager/item">
          <el-icon><Box /></el-icon>
          <span>物品借还</span>
        </el-menu-item>
        <el-menu-item index="/manager/hygiene">
          <el-icon><List /></el-icon>
          <span>卫生评分</span>
        </el-menu-item>
      </template>

      <!-- 4. 管理员菜单 -->
      <template v-if="userStore.userType === 3">
        <el-menu-item index="/admin/settings">
          <el-icon><Setting /></el-icon>
          <span>系统设置</span>
        </el-menu-item>
        <el-menu-item index="/admin/user">
          <el-icon><UserFilled /></el-icon>
          <span>账号管理</span>
        </el-menu-item>
        <el-menu-item index="/manager/notice"> <!-- 管理员也可以发公告，复用 -->
          <el-icon><Bell /></el-icon>
          <span>公告管理</span>
        </el-menu-item>
      </template>

    </el-menu>
  </el-aside>
</template>

<script setup>
import { useUserStore } from '../../store/user';
import { useRoute } from 'vue-router';
// 引入图标 (需要在 main.js 全局注册过，或者这里单独引入)
import {
  House,
  User,
  Tools,
  Position,
  Money,
  DataBoard,
  UserFilled,
  Stamp,
  OfficeBuilding,
  Bell, ToiletPaper, Box, List, Service, Setting
} from '@element-plus/icons-vue';

const userStore = useUserStore();
const route = useRoute();
</script>

<style scoped lang="scss">
.sidebar-container {
  background-color: var(--color-sidebar-bg);
  height: 100vh;
  overflow-y: auto;

  .logo {
    height: 60px;
    line-height: 60px;
    text-align: center;
    color: var(--color-text-inverse);
    font-size: 20px;
    font-weight: bold;
    background-color: var(--color-sidebar-logo-bg);
  }

  :deep(.el-menu) {
    border-right: none;
  }
}
</style>