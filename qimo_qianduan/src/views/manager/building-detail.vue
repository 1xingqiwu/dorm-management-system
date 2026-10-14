<template>
  <div class="building-detail">
    <!-- 1. 顶部：楼栋基本信息 -->
    <el-card class="info-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <span style="font-weight: bold; font-size: 18px;">
            <el-icon><OfficeBuilding /></el-icon> {{ buildingInfo.name }}
          </span>
          <el-tag>{{ buildingInfo.type === 1 ? '男生宿舍' : '女生宿舍' }}</el-tag>
        </div>
      </template>
      <el-descriptions :column="3" border>
        <el-descriptions-item label="楼栋ID">{{ buildingInfo.buildingId }}</el-descriptions-item>
        <el-descriptions-item label="总层数">{{ buildingInfo.totalFloor }} 层</el-descriptions-item>
        <el-descriptions-item label="位置">{{ buildingInfo.location || '校内' }}</el-descriptions-item>
        <el-descriptions-item label="描述">{{ buildingInfo.description || '暂无描述' }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <!-- 2. 中部：房间列表表格 -->
    <el-card style="margin-top: 20px;">
      <template #header>
        <span>房间列表 (共 {{ roomList.length }} 间)</span>
      </template>

      <el-table :data="roomList" border stripe v-loading="loading">
        <el-table-column prop="floorNumber" label="楼层" width="80" align="center" sortable />
        <el-table-column prop="roomNumber" label="房间号" width="120" align="center" sortable>
          <template #default="{ row }">
            <el-tag effect="dark">{{ row.roomNumber }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="category" label="户型" width="100" align="center" />

        <el-table-column label="入住情况" width="220">
          <template #default="{ row }">
            <el-progress
                :percentage="calculatePercentage(row)"
                :format="() => `${row.occupied}/${row.capacity}`"
                :status="getOccupancyStatus(row)"
                :stroke-width="18"
                text-inside
            />
          </template>
        </el-table-column>

        <el-table-column prop="electricityBalance" label="电费余额" width="120" align="center">
          <template #default="{ row }">
            <span :style="{ color: row.electricityBalance < 20 ? 'red' : 'green', fontWeight: 'bold' }">
              ¥ {{ row.electricityBalance }}
            </span>
          </template>
        </el-table-column>

        <el-table-column label="操作" min-width="120">
          <template #default="{ row }">
            <el-button type="primary" link @click="openStudentDialog(row)">
              查看成员
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 3. 底部：查看学生弹窗 -->
    <el-dialog v-model="dialogVisible" title="宿舍成员列表" width="600px">
      <el-table :data="studentList" border>
        <el-table-column prop="bedNumber" label="床号" width="80" align="center" />
        <el-table-column prop="name" label="姓名" width="100" align="center" />
        <el-table-column prop="studentNo" label="学号" width="120" />
        <el-table-column prop="className" label="班级" />
        <el-table-column prop="phone" label="电话" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { getBuildingDetailApi } from '../../api/building';
import { getRoomsByBuildingApi, getStudentsByRoomApi } from '../../api/room'; // 引入新API
import { OfficeBuilding } from '@element-plus/icons-vue';

const route = useRoute();
const loading = ref(false);
const buildingInfo = ref({});
const roomList = ref([]);

// 弹窗相关
const dialogVisible = ref(false);
const studentList = ref([]);

// 加载楼栋和房间数据
const loadData = async (id) => {
  loading.value = true;
  try {
    // 1. 查楼栋基本信息
    const infoRes = await getBuildingDetailApi(id);
    buildingInfo.value = infoRes;

    // 2. 查房间列表
    const roomRes = await getRoomsByBuildingApi(id);
    roomList.value = roomRes;
  } catch (e) {
    console.error(e);
  } finally {
    loading.value = false;
  }
};

// 点击“查看成员”
const openStudentDialog = async (row) => {
  studentList.value = []; // 先清空
  dialogVisible.value = true;
  try {
    const res = await getStudentsByRoomApi(row.roomId);
    studentList.value = res;
  } catch (e) {
    console.error(e);
  }
};

// 工具函数：计算进度条百分比
const calculatePercentage = (row) => {
  if (!row.capacity) return 0;
  return Math.floor((row.occupied / row.capacity) * 100);
};

// 工具函数：进度条颜色状态
const getOccupancyStatus = (row) => {
  if (row.occupied >= row.capacity) return 'exception'; // 满员(红)
  if (row.occupied === 0) return ''; // 空(蓝)
  return 'success'; // 有人且没满(绿)
};

// 监听路由参数变化 (切换楼栋时触发)
watch(
    () => route.params.id,
    (newId) => {
      if (newId) {
        loadData(newId);
      }
    },
    { immediate: true }
);
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>