<template>
  <el-card header="宿舍卫生检查记录">
    <el-table :data="list" stripe border>
      <el-table-column prop="checkDate" label="检查日期" width="180" />
      <el-table-column prop="score" label="得分" width="100" align="center">
        <template #default="{ row }">
          <span :style="{ color: row.score < 60 ? 'red' : 'green', fontWeight: 'bold', fontSize: '16px' }">
            {{ row.score }}
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="deductReason" label="扣分/违规原因" />
      <el-table-column prop="checkerName" label="检查人" width="120" />
    </el-table>
  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from '../../utils/request';

const list = ref([]);

const getList = async () => {
  // 调用上面写的 HygieneController.getMyHistory
  const res = await request.get('/hygiene/my');
  list.value = res;
};

onMounted(() => getList());
</script>