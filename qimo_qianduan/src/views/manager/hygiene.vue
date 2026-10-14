<template>
  <div class="hygiene-entry">
    <el-card header="卫生检查评分">
      <!-- 1. 选择楼栋和房间 -->
      <div class="filter-box" style="margin-bottom: 20px;">
        <el-select v-model="selectedBuilding" placeholder="选择楼栋" @change="loadRooms" style="width: 150px">
          <el-option
              v-for="item in userStore.managedBuildings"
              :key="item.buildingId"
              :label="item.name"
              :value="item.buildingId"
          />
        </el-select>
      </div>

      <!-- 2. 房间列表 -->
      <el-table :data="roomList" border stripe v-loading="loading">
        <el-table-column prop="roomNumber" label="宿舍号" width="100" align="center">
          <template #default="{ row }"><el-tag>{{ row.roomNumber }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="category" label="类型" width="100" />
        <el-table-column prop="lastCleanScore" label="上次得分" width="100">
          <template #default="{ row }">
            <span v-if="row.lastCleanScore">{{ row.lastCleanScore }}</span>
            <span v-else style="color:#ccc">无记录</span>
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="openDialog(row)">
              <el-icon><Edit /></el-icon> 打分
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 3. 打分弹窗 -->
    <el-dialog v-model="dialogVisible" :title="'录入卫生检查: ' + currentRoom.roomNumber" width="400px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="分数">
          <el-input-number v-model="form.score" :min="0" :max="100" />
        </el-form-item>
        <el-form-item label="扣分原因">
          <el-input v-model="form.deductReason" type="textarea" placeholder="例：地面有垃圾" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitScore">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useUserStore } from '../../store/user';
import request from '../../utils/request'; // 通用请求
import { getRoomsByBuildingApi } from '../../api/room'; // 复用之前的API
import { Edit } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';

const userStore = useUserStore();
const loading = ref(false);
const dialogVisible = ref(false);

const selectedBuilding = ref(null);
const roomList = ref([]);
const currentRoom = ref({});

const form = reactive({
  roomId: null,
  score: 100,
  deductReason: ''
});

// 加载房间
const loadRooms = async () => {
  if (!selectedBuilding.value) return;
  loading.value = true;
  try {
    const res = await getRoomsByBuildingApi(selectedBuilding.value);
    roomList.value = res;
  } finally {
    loading.value = false;
  }
};

// 打开打分窗口
const openDialog = (row) => {
  currentRoom.value = row;
  form.roomId = row.roomId;
  form.score = 100;
  form.deductReason = '';
  dialogVisible.value = true;
};

// 提交分数
const submitScore = async () => {
  try {
    await request.post('/hygiene/add', form);
    ElMessage.success('录入成功');
    dialogVisible.value = false;
    // 可以在这里刷新列表，或者只是关闭
  } catch (e) {
    console.error(e);
  }
};

// 初始化：默认选中第一个楼栋
onMounted(() => {
  if (userStore.managedBuildings.length > 0) {
    selectedBuilding.value = userStore.managedBuildings[0].buildingId;
    loadRooms();
  }
});
</script>