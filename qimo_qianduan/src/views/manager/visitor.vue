<template>
  <div class="visitor-manage">
    <el-card>
      <!-- 搜索 -->
      <template #header>
        <div class="filter-box">
          <el-input v-model="searchName" placeholder="访客姓名" style="width: 150px" clearable />
          <el-select v-model="searchStatus" placeholder="状态" style="width: 120px;" clearable>
            <el-option label="待审核" :value="0" />
            <el-option label="待来访" :value="1" />
            <el-option label="已离开" :value="3" />
          </el-select>
          <el-button type="primary" @click="getList">查询</el-button>
        </div>
      </template>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="visitorName" label="访客姓名" width="100" />
        <el-table-column prop="phone" label="联系电话" width="120" />
        <el-table-column prop="studentName" label="被访学生" width="150">
          <template #default="{ row }">
            {{ row.studentName }} <br/>
            <span style="font-size: 12px; color: var(--color-text-muted)">{{ row.roomInfo }}</span>
          </template>
        </el-table-column>
        <el-table-column label="来访时间" width="250">
          <template #default="{ row }">
            起: {{ formatTime(row.visitStartTime) }} <br/>
            <span v-if="row.visitEndTime">止: {{ formatTime(row.visitEndTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="reason" label="事由" />

        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag class="status-tag" :type="getStatusType(row.status)">{{ getStatusText(row.status) }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <!-- 待审核状态：显示通过/驳回 -->
            <div v-if="row.status === 0">
              <el-button type="success" size="small" @click="handleAudit(row, 1)">通过</el-button>
              <el-button type="danger" size="small" @click="handleAudit(row, 4)">驳回</el-button>
            </div>
            <!-- 待来访状态：显示确认离开 -->
            <div v-else-if="row.status === 1">
              <el-button type="warning" size="small" @click="handleAudit(row, 3)">确认离校</el-button>
            </div>
            <span v-else style="color: #ccc">已归档</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from '../../utils/request.js';
import dayjs from 'dayjs';
import { ElMessage, ElMessageBox } from 'element-plus';

const loading = ref(false);
const tableData = ref([]);
const searchName = ref('');
const searchStatus = ref(null);

const getList = async () => {
  loading.value = true;
  try {
    const res = await request.get('/visitor/list', {
      params: {
        name: searchName.value,
        status: searchStatus.value
      }
    });
    tableData.value = res;
  } finally {
    loading.value = false;
  }
};

const handleAudit = (row, status) => {
  let tip = '确定执行此操作吗？';
  if (status === 1) tip = `同意 ${row.visitorName} 的来访申请？`;
  if (status === 4) tip = `驳回 ${row.visitorName} 的申请？`;
  if (status === 3) tip = `确认 ${row.visitorName} 已经离开宿舍楼？`;

  ElMessageBox.confirm(tip, '提示', { type: 'warning' }).then(async () => {
    await request.post('/visitor/audit', null, {
      params: { id: row.visitorId, status: status }
    });
    ElMessage.success('操作成功');
    getList();
  });
};

// 工具函数
const formatTime = (t) => t ? dayjs(t).format('YYYY-MM-DD HH:mm') : '';
// 状态枚举与后端一致：0-待审核, 1-待来访, 2-已进入, 3-已离开, 4-已驳回
const getStatusText = (s) => ({0:'待审核', 1:'待来访', 2:'已进入', 3:'已离开', 4:'已驳回'}[s] ?? '未知');
const getStatusType = (s) => ({0:'warning', 1:'success', 2:'primary', 3:'info', 4:'danger'}[s] ?? 'info');

onMounted(() => getList());
</script>

<style scoped>
.filter-box { display: flex; align-items: center; flex-wrap: wrap; gap: 10px; }

/* 状态标签统一固定宽度，保证所有矩形框长度完全一致（与文字内容无关） */
.status-tag {
  width: 64px;
  justify-content: center;
}
</style>