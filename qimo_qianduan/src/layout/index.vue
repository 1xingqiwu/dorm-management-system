<template>
  <el-container class="app-wrapper">
    <!-- 1. PC 端侧边栏 (用 CSS 控制：手机端隐藏) -->
    <Sidebar class="hidden-xs-only" />

    <!-- 2. 手机端侧边栏 (抽屉式) -->
    <el-drawer
        v-model="drawerVisible"
        direction="ltr"
        :with-header="false"
        size="60%"
        class="mobile-menu"
    >
      <Sidebar />
    </el-drawer>

    <el-container class="main-container">
      <el-header style="padding: 0;">
        <!-- 传一个回调给 Header，让它能打开抽屉 -->
        <Header @toggle-menu="drawerVisible = true" />
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import Sidebar from './components/Sidebar.vue';
import Header from './components/Header.vue';
import 'element-plus/theme-chalk/display.css'; // 引入 Element 的断点隐藏类

const drawerVisible = ref(false);
</script>


<style scoped>
/* 媒体查询：在屏幕小于 768px (手机) 时隐藏 PC 侧边栏 */
@media (max-width: 768px) {
  .hidden-xs-only {
    display: none !important;
  }
}

.app-wrapper {
  height: 100vh;
  width: 100%;
}
.main-container {
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
}

/* 核心修改：给主内容区添加深空背景 */
.el-main {
  /* 这里的背景色要和 AI 页面的一致 */
  background: radial-gradient(circle at top left, var(--color-page-bg-start) 0%, var(--color-page-bg-end) 100%) !important;
  padding: 20px;
  overflow-y: auto;

  /* 滚动条美化，防止原生白色滚动条破坏美感 */
  &::-webkit-scrollbar { width: 6px; }
  &::-webkit-scrollbar-thumb { background: var(--color-scrollbar-thumb); border-radius: 3px; }
  &::-webkit-scrollbar-track { background: transparent; }
}

/* 头部保持透明或深色，防止出现白边 */
.el-header {
  padding: 0;
  background: transparent;
}

.el-main { background-color: #f0f2f5; padding: 10px; /* 手机端 Padding 改小点 */ overflow-y: auto; }
</style>