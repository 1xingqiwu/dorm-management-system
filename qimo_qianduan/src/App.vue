<template>
  <!-- 全局配置组件，用于设置 Element Plus 的语言为中文 -->
  <el-config-provider :locale="locale">
    <!-- 路由出口：页面会在这里渲染 -->
    <router-view />
  </el-config-provider>
</template>

<script setup>
import { ref } from 'vue'
// 引入 Element Plus 的中文语言包
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

const locale = ref(zhCn)
</script>

<style>
/* 全局样式重置 */
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: #f0f2f5; /* 统一背景色 */
}

#app {
  width: 100%;
  height: 100%;
}

/* 在全局样式或者 App.vue 里加 */
@media (max-width: 768px) {
  .el-card__body {
    padding: 10px !important; /* 减小卡片内边距 */
  }

  /* 强制表格允许横向滚动 */
  .el-table {
    width: 100%;
    overflow-x: auto;
  }
}
</style>