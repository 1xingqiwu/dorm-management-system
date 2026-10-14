<template>
  <div class="notice-manage">
    <el-card>
      <template #header>
        <div style="display: flex; justify-content: space-between;">
          <span>公告列表</span>
          <el-button type="primary" @click="openEditDialog()">发布公告</el-button>
        </div>
      </template>

      <el-table :data="list" border stripe>
        <el-table-column prop="title" label="标题" />
        <el-table-column label="内容摘要">
          <template #default="{ row }">
            {{ getTextFromHtml(row.content).substring(0, 30) }}...
          </template>
        </el-table-column>
        <el-table-column prop="publisherName" label="发布人" width="120" />
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <!-- 🟢 新增：详情按钮 -->
            <el-button link type="success" @click="openViewDialog(row)">详情</el-button>
            <el-button link type="primary" @click="openEditDialog(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 🟢 1. 预览详情弹窗 (只读) -->
    <el-dialog v-model="viewVisible" title="公告预览" width="800px" top="5vh">
      <div class="article-preview">
        <h2 class="article-title">{{ currentNotice.title }}</h2>
        <div class="article-meta">
          <span>发布人：{{ currentNotice.publisherName }}</span>
          <span style="margin-left: 20px">时间：{{ currentNotice.createTime }}</span>
        </div>
        <el-divider />
        <el-scrollbar height="60vh">
          <!-- 这里使用 v-html 渲染富文本 -->
          <div class="article-content" v-html="currentNotice.content"></div>
        </el-scrollbar>
      </div>
      <template #footer>
        <el-button @click="viewVisible = false">关闭</el-button>
      </template>
    </el-dialog>

    <!-- 🔵 2. 编辑/发布弹窗 (写操作) -->
    <el-dialog
        v-model="editVisible"
        :title="form.noticeId ? '编辑公告' : '发布公告'"
        width="900px"
        top="5vh"
        :destroy-on-close="true"
    >
      <el-form :model="form" label-width="60px">
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="请输入公告标题" size="large" />
        </el-form-item>
        <el-form-item label="内容">
          <div style="border: 1px solid #ccc; width: 100%;">
            <Toolbar
                style="border-bottom: 1px solid #ccc"
                :editor="editorRef"
                :defaultConfig="toolbarConfig"
                mode="default"
            />
            <Editor
                style="height: 400px; overflow-y: hidden;"
                v-model="form.content"
                :defaultConfig="editorConfig"
                mode="default"
                @onCreated="handleCreated"
            />
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="editVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">发布</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, shallowRef, onBeforeUnmount } from 'vue';
import request from '../../utils/request';
import { ElMessage, ElMessageBox } from 'element-plus';
import '@wangeditor/editor/dist/css/style.css';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';

// --- 变量定义 ---
const list = ref([]);
const editVisible = ref(false); // 控制编辑框
const viewVisible = ref(false); // 控制预览框
const currentNotice = ref({});  // 当前查看的公告

const form = reactive({ noticeId: null, title: '', content: '' });

// --- 编辑器配置 ---
const editorRef = shallowRef();
const toolbarConfig = {};
const editorConfig = { placeholder: '请输入公告内容...' };

onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});

const handleCreated = (editor) => {
  editorRef.value = editor;
};

// --- 业务逻辑 ---
const getList = async () => {
  const res = await request.get('/notice/list');
  list.value = res;
};

const getTextFromHtml = (html) => {
  if (!html) return '';
  return html.replace(/<[^>]+>/g, "");
};

// 打开预览
const openViewDialog = (row) => {
  currentNotice.value = row;
  viewVisible.value = true;
};

// 打开编辑
const openEditDialog = (row) => {
  if (row) {
    Object.assign(form, row);
  } else {
    form.noticeId = null;
    form.title = '';
    form.content = '';
  }
  editVisible.value = true;
};

const submit = async () => {
  const url = form.noticeId ? '/notice/update' : '/notice/add';
  await request.post(url, form);
  ElMessage.success('操作成功');
  editVisible.value = false;
  getList();
};

const handleDelete = (row) => {
  ElMessageBox.confirm('确认删除该公告吗？').then(async () => {
    await request.post(`/notice/delete/${row.noticeId}`);
    ElMessage.success('删除成功');
    getList();
  });
};

onMounted(() => getList());
</script>

<style scoped lang="scss">
/* 预览样式：必须和学生端保持一致 */
.article-preview {
  padding: 0 20px;
}
.article-title { text-align: center; font-size: 24px; color: #303133; margin-bottom: 10px; }
.article-meta { text-align: center; color: #909399; font-size: 13px; margin-bottom: 20px; }

/* 核心内容样式 */
.article-content {
  font-size: 16px;
  line-height: 1.8;
  color: #e2e8f0; /* 核心：文字改白 */
  padding: 10px 5px;
  font-family: sans-serif;

  /* 确保段落内的文字也是白的 */
  :deep(p) {
    margin-bottom: 10px;
    text-indent: 0;
    color: #e2e8f0;
  }

  /* 列表样式 */
  :deep(ul), :deep(ol) {
    margin: 10px 0;
    padding-left: 20px;
    color: #e2e8f0;
  }
  :deep(ul) { list-style-type: disc; }
  :deep(ol) { list-style-type: decimal; }

  /* 加粗和斜体 */
  :deep(strong), :deep(b) {
    font-weight: bold;
    color: var(--color-primary); /* 重点文字高亮 */
  }
  :deep(em), :deep(i) { font-style: italic; }

  /* 图片 */
  :deep(img) {
    max-width: 100%;
    height: auto;
    border-radius: 4px;
    border: 1px solid rgba(255,255,255,0.1);
  }

  /* 引用块 */
  :deep(blockquote) {
    border-left: 4px solid var(--color-primary);
    margin: 10px 0;
    padding-left: 10px;
    color: #94a3b8;
    background-color: rgba(255,255,255,0.05);
  }
}
</style>