<template>
  <div class="counselor-hygiene">
    <el-card header="班级宿舍卫生检查统计">
      <el-table :data="list" border stripe v-loading="loading">
        <el-table-column prop="checkDate" label="检查日期" width="180" sortable />

        <el-table-column prop="roomName" label="宿舍" width="200">
          <template #default="{ row }">
            <el-tag effect="plain">{{ row.roomName }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="score" label="得分" width="120" sortable align="center">
          <template #default="{ row }">
            <span :style="{ color: row.score < 60 ? 'red' : (row.score < 80 ? 'orange' : 'green'), fontWeight: 'bold' }">
              {{ row.score }}
            </span>
          </template>
        </el-table-column>

        <el-table-column prop="deductReason" label="扣分/违规原因" />
        <el-table-column prop="checkerName" label="检查人" width="120" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getCounselorHygieneListApi } from '../../api/hygiene';

const loading = ref(false);
const list = ref([]);

const getList = async () => {
  loading.value = true;
  try {
    const res = await getCounselorHygieneListApi();
    list.value = res;
  } finally {
    loading.value = false;
  }
};

onMounted(() => getList());
</script>