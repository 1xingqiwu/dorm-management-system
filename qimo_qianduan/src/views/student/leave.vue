<template>
  <div class="leave-container">
    <el-card>
      <template #header>
        <div class="header-box">
          <span>我的请假记录</span>
          <el-button type="primary" @click="dialogVisible = true">发起请假</el-button>
        </div>
      </template>

      <!-- 列表展示 -->
      <el-table :data="tableData" stripe style="width: 100%" v-loading="loading">
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag>{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="请假时间" width="320">
          <template #default="{ row }">
            {{ formatTime(row.startTime) }} 至 {{ formatTime(row.endTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="reason" label="事由" show-overflow-tooltip />
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="申请时间" width="180">
          <template #default="{ row }">
            {{ formatTime(row.createTime) }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 申请弹窗 -->
    <el-dialog v-model="dialogVisible" title="填写请假条" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="请假类型">
          <el-radio-group v-model="form.type">
            <el-radio label="事假">事假</el-radio>
            <el-radio label="病假">病假</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="时间范围">
          <el-date-picker
              v-model="dateRange"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始"
              end-placeholder="结束"
              value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>

        <el-form-item label="请假事由">
          <el-input v-model="form.reason" type="textarea" :rows="3" placeholder="请详细填写原因" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交申请</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import request from '../../utils/request';
import dayjs from 'dayjs';
import { ElMessage } from 'element-plus';

const loading = ref(false);
const dialogVisible = ref(false);
const tableData = ref([]);
const dateRange = ref([]); // 暂存时间范围

const form = reactive({
  type: '事假',
  reason: '',
  startTime: '',
  endTime: ''
});

// 获取列表
const getList = async () => {
  loading.value = true;
  try {
    const res = await request.get('/leave/my');
    tableData.value = res;
  } finally {
    loading.value = false;
  }
};

// 提交申请
const handleSubmit = async () => {
  if (!dateRange.value || dateRange.value.length < 2) {
    ElMessage.warning('请选择请假时间');
    return;
  }
  // 赋值时间
  form.startTime = dateRange.value[0];
  form.endTime = dateRange.value[1];

  try {
    await request.post('/leave/apply', form);
    ElMessage.success('提交成功');
    dialogVisible.value = false;
    // 重置表单并刷新
    form.reason = '';
    dateRange.value = [];
    getList();
  } catch (e) {
    console.error(e);
  }
};

// 格式化工具
const formatTime = (time) => dayjs(time).format('YYYY-MM-DD HH:mm');
const getStatusText = (s) => ['待审批', '已批准', '已驳回'][s] || '未知';
const getStatusType = (s) => ['info', 'success', 'danger'][s] || 'info';

onMounted(() => {
  getList();
});
</script>

<style scoped>
.header-box { display: flex; justify-content: space-between; align-items: center; }
</style>