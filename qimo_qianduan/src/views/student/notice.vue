<template>
  <div class="student-notice">
    <el-card header="通知公告">
      <div v-if="list.length === 0" class="empty-box">暂无公告</div>

      <el-timeline v-else>
        <el-timeline-item
            v-for="(item, index) in list"
            :key="index"
            :timestamp="item.createTime"
            placement="top"
            :type="index === 0 ? 'primary' : ''"
            :hollow="index === 0"
        >
          <el-card shadow="hover" class="notice-card" @click="showDetail(item)">
            <div class="card-header">
              <h4 class="title">{{ item.title }}</h4>
              <el-tag size="small" effect="plain">{{ item.publisherName || '管理员' }}</el-tag>
            </div>
            <p class="summary">
              {{ truncateContent(item.content) }}
              <span v-if="item.content.length > 80" class="read-more"> 全文 &gt;</span>
            </p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-card>

    <!-- 🟢 优化后的详情弹窗 -->
    <el-dialog
        v-model="dialogVisible"
        width="800px"
        top="5vh"
        class="notice-dialog"
        :lock-scroll="true"
    >
      <!-- 自定义标题栏 -->
      <template #header>
        <div class="dialog-header">
          <el-icon class="icon"><Document /></el-icon>
          <span>公告详情</span>
        </div>
      </template>

      <div class="article-container">
        <!-- 标题区 -->
        <h2 class="article-title">{{ currentNotice.title }}</h2>
        <div class="article-meta">
          <span><el-icon><User /></el-icon> 发布人：{{ currentNotice.publisherName || '管理员' }}</span>
          <span class="divider">|</span>
          <span><el-icon><Clock /></el-icon> 时间：{{ currentNotice.createTime }}</span>
        </div>

        <el-divider border-style="dashed" />

        <!-- 内容区：使用 el-scrollbar 实现内部滚动 -->
        <el-scrollbar height="60vh">
          <div class="article-content" v-html="currentNotice.content">
          </div>
        </el-scrollbar>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="dialogVisible = false" size="large">我已知晓</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getNoticeListApi } from '../../api/notice';
import { Document, User, Clock } from '@element-plus/icons-vue'; // 引入图标

const list = ref([]);
const dialogVisible = ref(false);
const currentNotice = ref({});

const getList = async () => {
  const res = await getNoticeListApi();
  list.value = res;
};

const truncateContent = (content) => {
  if (!content) return '';
  return content.length > 80 ? content.substring(0, 80) + '...' : content;
};

const showDetail = (item) => {
  currentNotice.value = item;
  dialogVisible.value = true;
};

onMounted(() => getList());
</script>

<style scoped lang="scss">
/* ... 列表卡片样式保持不变 ... */
.notice-card { /* ...略... */ }
.card-header { /* ...略... */ }
.summary { /* ...略... */ }

/* ... 弹窗头部样式保持不变 ... */
.dialog-header { /* ...略... */ }
.article-container { padding: 0 20px; }
.article-title { /* ...略... */ }
.article-meta { /* ...略... */ }

/* 核心修改：文章内容样式 */
/* 修复：文章内容样式 (适配深色模式) */
.article-content {
  font-size: 16px;
  line-height: 1.8;

  /* 核心修改：把 #333 改为 #e2e8f0 (亮灰白) */
  color: #e2e8f0;

  padding: 10px 5px;
  font-family: sans-serif;

  /* 确保段落内的文字也是白的 */
  :deep(p) {
    margin-bottom: 10px;
    text-indent: 0;
    color: #e2e8f0;
  }

  /* 还原列表样式 */
  :deep(ul), :deep(ol) {
    margin: 10px 0;
    padding-left: 20px;
    color: #e2e8f0; /* 列表文字变白 */
  }
  :deep(ul) { list-style-type: disc; }
  :deep(ol) { list-style-type: decimal; }

  /* 还原加粗和斜体 */
  :deep(strong), :deep(b) {
    font-weight: bold;
    color: var(--color-primary); /* 重点文字用蓝色高亮 */
  }
  :deep(em), :deep(i) { font-style: italic; }

  /* 图片自适应 */
  :deep(img) {
    max-width: 100%;
    height: auto;
    border-radius: 4px;
    border: 1px solid rgba(255,255,255,0.1); /* 给图片加个微弱边框 */
  }

  /* 引用块样式 */
  :deep(blockquote) {
    border-left: 4px solid var(--color-primary);
    margin: 10px 0;
    padding-left: 10px;
    color: #94a3b8; /* 引用文字用淡灰色 */
    background-color: rgba(255,255,255,0.05); /* 背景半透明 */
  }
}
</style>