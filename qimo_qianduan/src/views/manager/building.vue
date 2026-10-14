<template>
  <div class="building-manage">
    <el-card>
      <template #header>
        <div class="filter-box">
          <el-input v-model="roomNumber" placeholder="房间号" style="width: 200px" clearable @clear="getList" />
          <el-button type="primary" @click="getList">
            <el-icon><Search /></el-icon> 查询
          </el-button>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="floorNumber" label="楼层" width="80" sortable />
        <el-table-column prop="roomNumber" label="房间号" width="120" sortable />
        <el-table-column prop="category" label="户型" width="100" />

        <el-table-column label="入住情况" width="200">
          <template #default="{ row }">
            <el-progress
                :percentage="(row.occupied / row.capacity) * 100"
                :format="() => `${row.occupied}/${row.capacity}`"
                :status="getStatus(row)"
            />
          </template>
        </el-table-column>

        <el-table-column prop="electricityBalance" label="电费余额" width="120">
          <template #default="{ row }">
            <span :style="{ color: row.electricityBalance < 10 ? 'red' : 'green' }">
              ¥ {{ row.electricityBalance }}
            </span>
          </template>
        </el-table-column>

        <el-table-column label="操作" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="showDetail(row)">查看学生</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 后面可以加个弹窗显示该房间的学生列表，暂时留空 -->
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from '../../utils/request';
import { Search } from '@element-plus/icons-vue';

const loading = ref(false);
const tableData = ref([]);
const roomNumber = ref('');

const getList = async () => {
  loading.value = true;
  try {
    const res = await request.get('/room/list', {
      params: { roomNumber: roomNumber.value }
    });
    tableData.value = res;
  } finally {
    loading.value = false;
  }
};

const getStatus = (row) => {
  if (row.occupied >= row.capacity) return 'success'; // 满员
  if (row.occupied === 0) return 'exception'; // 空房
  return ''; // 有空位
};

const showDetail = (row) => {
  // 预留功能：点击后弹窗显示 tb_student where room_id = row.roomId
  alert('查看 ' + row.roomNumber + ' 的学生列表 (待开发)');
};

onMounted(() => getList());
</script>

<style scoped>
.filter-box { display: flex; align-items: center; flex-wrap: wrap; gap: 10px; }
</style>