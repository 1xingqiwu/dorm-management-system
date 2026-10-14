<template>
  <div class="student-manage">
    <el-card>
      <!-- 搜索栏 -->
      <template #header>
        <div class="filter-box">
          <el-input v-model="queryParams.name" placeholder="姓名" style="width: 150px" clearable />
          <el-input v-model="queryParams.studentNo" placeholder="学号" style="width: 150px;" clearable />
          <el-input v-model="queryParams.className" placeholder="班级" style="width: 150px;" clearable />
          <el-button type="primary" @click="getList">
            <el-icon><Search /></el-icon> 查询
          </el-button>
        </div>
      </template>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="studentNo" label="学号" width="120" sortable />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="className" label="班级" width="150" />
        <el-table-column prop="phone" label="手机号" width="120" />

        <el-table-column label="宿舍信息" width="180">
          <template #default="{ row }">
            <span v-if="row.buildingName">
              {{ row.buildingName }} - {{ row.roomNumber }}
            </span>
            <el-tag type="info" v-else>未分配</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === '在校' ? 'success' : 'warning'">{{ row.status }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="showDetail(row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 详情弹窗 (简单展示) -->
    <el-dialog v-model="dialogVisible" title="学生详情" width="500px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="姓名">{{ currentRow.name }}</el-descriptions-item>
        <el-descriptions-item label="学号">{{ currentRow.studentNo }}</el-descriptions-item>
        <el-descriptions-item label="紧急联系人">{{ currentRow.emergencyContact || '无' }}</el-descriptions-item>
        <el-descriptions-item label="紧急电话">{{ currentRow.emergencyPhone || '无' }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import request from '../../utils/request.js';
import { Search } from '@element-plus/icons-vue';

const loading = ref(false);
const tableData = ref([]);
const dialogVisible = ref(false);
const currentRow = ref({});

const queryParams = reactive({
  name: '',
  studentNo: '',
  className: ''
});

const getList = async () => {
  loading.value = true;
  try {
    const res = await request.get('/student/list/my', { params: queryParams });
    tableData.value = res;
  } finally {
    loading.value = false;
  }
};

const showDetail = (row) => {
  currentRow.value = row;
  dialogVisible.value = true;
};


onMounted(() => getList());
</script>

<style scoped>
.filter-box { display: flex; align-items: center; flex-wrap: wrap; gap: 10px; }
</style>