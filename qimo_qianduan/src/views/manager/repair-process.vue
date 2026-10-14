<template>
  <div class="manager-repair">
    <el-card>
      <template #header>
        <div class="header">
          <span>报修处理中心</span>
          <el-radio-group v-model="queryStatus" @change="getList">
            <el-radio-button :label="0">待处理</el-radio-button>
            <el-radio-button :label="1">已完成</el-radio-button>
          </el-radio-group>
        </div>
      </template>

      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="repairType" label="类型" width="100">
          <template #default="{ row }"><el-tag>{{ row.repairType }}</el-tag></template>
        </el-table-column>

        <el-table-column label="报修图片" width="100">
          <template #default="{ row }">
            <el-image
                v-if="row.imageUrls"
                style="width: 50px; height: 50px"
                :src="row.imageUrls"
                :preview-src-list="[row.imageUrls]"
                preview-teleported
            />
          </template>
        </el-table-column>

        <el-table-column prop="description" label="故障描述" />
        <el-table-column prop="submitTime" label="提交时间" width="180" />

        <el-table-column label="操作" width="150" v-if="queryStatus === 0">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="handleFinish(row)">
              <el-icon><Check /></el-icon> 标记完成
            </el-button>
          </template>
        </el-table-column>

        <el-table-column prop="finishTime" label="完成时间" width="180" v-else />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from '../../utils/request';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Check } from '@element-plus/icons-vue';

const loading = ref(false);
const tableData = ref([]);
const queryStatus = ref(0);

const getList = async () => {
  loading.value = true;
  const res = await request.get('/repair/list', {
    params: { status: queryStatus.value }
  });
  tableData.value = res;
  loading.value = false;
};

const handleFinish = (row) => {
  ElMessageBox.confirm('确认该故障已维修完成吗？', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'success'
  }).then(async () => {
    // 调用处理接口
    await request.post('/repair/finish', null, {
      params: { repairId: row.repairId }
    });
    ElMessage.success('处理成功');
    getList(); // 刷新列表
  });
};

onMounted(() => getList());
</script>

<style scoped>
.header { display: flex; justify-content: space-between; align-items: center; }
</style>