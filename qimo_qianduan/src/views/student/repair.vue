<template>
  <div class="repair-container">
    <el-card>
      <template #header>
        <div class="header" style="display: flex; justify-content: space-between; align-items: center;">
          <span style="font-weight: bold; font-size: 16px;">我的报修记录</span>
          <el-button type="primary" @click="dialogVisible = true">
            在线报修
          </el-button>
        </div>
      </template>

      <!-- 报修列表 -->
      <el-table :data="tableData" v-loading="loading">
        <el-table-column prop="repairType" label="类型" width="100">
          <template #default="{ row }"><el-tag>{{ row.repairType }}</el-tag></template>
        </el-table-column>
        <el-table-column prop="description" label="故障描述" />
        <el-table-column label="图片" width="120">
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
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status===0?'warning':'success'">
              {{ row.status===0?'待处理':'已修好' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="submitTime" label="提交时间" width="180" />
      </el-table>
    </el-card>

    <!-- 报修弹窗 -->
    <el-dialog v-model="dialogVisible" title="填写报修单" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="报修类型">
          <el-select v-model="form.repairType" placeholder="请选择">
            <el-option label="水电维修" value="水电维修" />
            <el-option label="家具维修" value="家具维修" />
            <el-option label="网络维修" value="网络维修" />
          </el-select>
        </el-form-item>

        <el-form-item label="故障描述">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>

        <el-form-item label="现场照片">
          <el-upload
              class="avatar-uploader"
              action="/api/file/upload"
              :show-file-list="false"
              :headers="headers"
              :on-success="handleUploadSuccess"
              name="file"
          >
            <img v-if="form.imageUrls" :src="form.imageUrls" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handleSubmit" type="primary">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import request from '../../utils/request';
import { useUserStore } from '../../store/user';
import { Plus } from '@element-plus/icons-vue'; // 记得引入图标
import { ElMessage } from 'element-plus';

const userStore = useUserStore();
const loading = ref(false);
const dialogVisible = ref(false);
const tableData = ref([]);

// 上传需要携带 Token
const headers = { Authorization: `Bearer ${userStore.token}` };

const form = reactive({
  repairType: '',
  description: '',
  imageUrls: ''
});

const getList = async () => {
  loading.value = true;
  const res = await request.get('/repair/my');
  tableData.value = res;
  loading.value = false;
};

// 上传成功回调
const handleUploadSuccess = (res) => {
  if (res.code === 200) {
    form.imageUrls = res.data; // 后端返回的是完整 URL
    ElMessage.success('图片上传成功');
  } else {
    ElMessage.error(res.msg);
  }
};

const handleSubmit = async () => {
  await request.post('/repair/submit', form);
  ElMessage.success('报修提交成功');
  dialogVisible.value = false;
  getList();
};

onMounted(() => getList());
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover { border-color: var(--color-primary); }
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
}
.avatar { width: 100px; height: 100px; display: block; }
</style>