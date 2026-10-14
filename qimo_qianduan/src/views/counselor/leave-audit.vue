<template>
  <div class="audit-container">
    <el-card>
      <template #header>
        <div class="header">
          <span>请假审批</span>
          <el-radio-group v-model="queryStatus" @change="getList">
            <el-radio-button :label="0">待审批</el-radio-button>
            <el-radio-button :label="1">已批准</el-radio-button>
            <el-radio-button :label="2">已驳回</el-radio-button>
          </el-radio-group>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column label="申请人" width="180">
          <template #default="{ row }">
            <!-- 显示：王小明 (2023001) -->
            {{ row.studentName }} <span style="color: var(--color-text-muted)">({{ row.studentNo }})</span>
          </template>
        </el-table-column>

        <el-table-column prop="type" label="类型" width="80">
          <template #default="{ row }"><el-tag>{{ row.type }}</el-tag></template>
        </el-table-column>
        <el-table-column label="时间" width="300">
          <template #default="{ row }">{{ formatTime(row.startTime) }} - {{ formatTime(row.endTime) }}</template>
        </el-table-column>
        <el-table-column prop="reason" label="事由" />

        <el-table-column label="操作" width="200" v-if="queryStatus === 0">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="handleAudit(row, 1)">批准</el-button>
            <el-button type="danger" size="small" @click="handleAudit(row, 2)">驳回</el-button>
          </template>
        </el-table-column>

        <el-table-column prop="auditRemark" label="审批意见" v-else />
      </el-table>
    </el-card>

    <!-- 审批弹窗 -->
    <el-dialog v-model="dialogVisible" title="审批确认" width="400px">
      <p>确认要
        <span :style="{color: currentStatus===1?'green':'red', fontWeight:'bold'}">
          {{ currentStatus===1?'批准':'驳回' }}
        </span>
        该申请吗？
      </p>
      <el-input v-model="auditRemark" type="textarea" placeholder="请输入审批意见（选填）" />
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAudit">确认提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from '../../utils/request';
import dayjs from 'dayjs';
import { ElMessage } from 'element-plus';

const loading = ref(false);
const tableData = ref([]);
const queryStatus = ref(0); // 默认看待审批

const dialogVisible = ref(false);
const currentId = ref(null);
const currentStatus = ref(1);
const auditRemark = ref('');

const getList = async () => {
  loading.value = true;
  try {
    const res = await request.get('/leave/audit/list', {
      params: { status: queryStatus.value }
    });
    tableData.value = res;
  } finally {
    loading.value = false;
  }
};

const handleAudit = (row, status) => {
  currentId.value = row.applyId;
  currentStatus.value = status;
  auditRemark.value = status === 1 ? '同意' : ''; // 默认文案
  dialogVisible.value = true;
};

const submitAudit = async () => {
  try {
    await request.post('/leave/audit', {
      applyId: currentId.value,
      status: currentStatus.value,
      auditRemark: auditRemark.value
    });
    ElMessage.success('操作成功');
    dialogVisible.value = false;
    getList(); // 刷新列表
  } catch(e) { console.error(e); }
};

const formatTime = (t) => dayjs(t).format('YYYY-MM-DD HH:mm');

onMounted(() => getList());
</script>

<style scoped>
/* 卡片头部：标题左对齐、筛选按钮组右对齐，垂直居中，间距统一 */
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 12px;
  line-height: 1;
}

.header > span {
  font-size: 16px;
  font-weight: 600;
}

/* 窄屏下按钮组不挤压、不换行错位 */
.header .el-radio-group {
  flex-shrink: 0;
}
</style>