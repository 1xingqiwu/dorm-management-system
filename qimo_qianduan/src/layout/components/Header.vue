<template>
  <div class="header-container">
    <div class="left">
      <!-- 手机端显示的菜单按钮 -->
      <div class="mobile-toggle" @click="$emit('toggleMenu')">
        <el-icon size="24"><Menu /></el-icon>
      </div>
      <span class="welcome-text">欢迎登录，{{ userStore.userInfo.nickname || userStore.userInfo.username }}</span>
    </div>
    <div class="right">
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          <el-avatar :size="32" src="../../../public/OIP.jpg" />
          <span class="username">{{ userStore.userInfo.username }}</span>
          <el-icon class="el-icon--right"><arrow-down /></el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">个人中心</el-dropdown-item>
            <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '../../store/user';
import {ArrowDown, Menu} from '@element-plus/icons-vue';
import { useRouter } from 'vue-router';

const userStore = useUserStore();
const router = useRouter();

const handleCommand = (command) => {
  if (command === 'logout') {
    userStore.logout();
  } else if (command === 'profile') {
    router.push('/profile')
  }
};
</script>

<style scoped lang="scss">
/* 默认隐藏菜单按钮 */
.mobile-toggle { display: none; cursor: pointer; margin-right: 10px; }

/* 手机端显示菜单按钮 */
@media (max-width: 768px) {
  .mobile-toggle { display: block; }
  .welcome-text { font-size: 12px; } /* 欢迎语字体改小 */
}

.header-container {
  height: 60px;
  background-color: var(--color-header-bg);
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;

  .right {
    .el-dropdown-link {
      display: flex;
      align-items: center;
      cursor: pointer;
      .username {
        margin-left: 8px;
        margin-right: 4px;
        font-weight: 500;
      }
    }
  }
}
.welcome-text {
  color: var(--color-text-header);
  .username {
    color: var(--color-text-inverse);
    font-weight: 600;
    margin-left: 4px;
  }
}
</style>