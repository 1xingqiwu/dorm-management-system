<template>
  <div class="profile-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>个人详细信息</span>
          <el-button type="primary" link @click="openEditDialog">修改联系方式</el-button>
        </div>
      </template>

      <!-- 加载中骨架屏 -->
      <el-skeleton :rows="5" animated v-if="loading" />

      <!-- 信息展示 -->
      <el-descriptions v-else :column="2" border>
        <el-descriptions-item label="姓名">{{ profile.name }}</el-descriptions-item>
        <el-descriptions-item label="学号">{{ profile.studentNo }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ profile.gender }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ profile.phone || '未填写' }}</el-descriptions-item>

        <el-descriptions-item label="所属院系">{{ profile.collegeName }}</el-descriptions-item>
        <el-descriptions-item label="专业班级">{{ profile.majorName }} - {{ profile.className }}</el-descriptions-item>

        <el-descriptions-item label="宿舍楼栋">
          <el-tag size="small">{{ profile.buildingName || '未分配' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="宿舍号">
          <el-tag type="success" size="small">{{ profile.roomNumber || '未分配' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="床位号">
          <el-tag type="warning" size="small">{{ profile.bedNumber ? profile.bedNumber + '号床' : '未分配' }}</el-tag>
        </el-descriptions-item>

        <el-descriptions-item label="辅导员">{{ profile.counselorName }}</el-descriptions-item>
        <el-descriptions-item label="辅导员电话">{{ profile.counselorPhone }}</el-descriptions-item>
      </el-descriptions>

      <el-card style="margin-top: 20px;" header="我的舍友">
        <el-table :data="roommates" stripe border empty-text="暂无舍友，看来你是独享豪宅！">
          <el-table-column prop="bedNumber" label="床号" width="80" align="center">
            <template #default="{ row }">
              <el-tag effect="dark" type="info">{{ row.bedNumber }}号床</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="120" />
          <el-table-column prop="gender" label="性别" width="80" />
          <el-table-column prop="majorName" label="专业" />
          <el-table-column prop="className" label="班级" />
          <el-table-column prop="phone" label="联系电话" />
        </el-table>
      </el-card>
    </el-card>

    <!-- 修改信息的弹窗 -->
    <!-- 弹窗 -->
    <el-dialog v-model="dialogVisible" title="修改联系信息" width="500px">
      <el-form :model="form" label-width="120px">
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="紧急联系人">
          <el-input v-model="form.emergencyContact" />
        </el-form-item>
        <el-form-item label="紧急联系电话">
          <el-input v-model="form.emergencyPhone" />
        </el-form-item>
      </el-form>
      <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
    </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { getStudentProfileApi, updateProfileApi, getMyRoommatesApi } from '../../api/student';
import { ElMessage } from 'element-plus';

const loading = ref(true);
const dialogVisible = ref(false);
// 定义舍友数据
const roommates = ref([]);
const profile = ref({});


const getProfile = async () => {
  try {
    const data = await getStudentProfileApi();
    profile.value = data;

    // 获取完个人信息后，获取舍友
    const res = await getMyRoommatesApi();
    roommates.value = res;
  } catch (err) {
    console.error(err);
  } finally {
    loading.value = false;
  }
};


// 定义表单数据
const form = reactive({
  phone: '',
  emergencyContact: '',
  emergencyPhone: ''
});

// 打开弹窗时，回填数据
const openEditDialog = () => {
  form.phone = profile.value.phone;
  form.emergencyContact = profile.value.emergencyContact; // 注意后端 VO 要返回这俩字段，如果没返回要去加
  form.emergencyPhone = profile.value.emergencyPhone;
  dialogVisible.value = true;
};

// 提交修改
const handleSubmit = async () => {
  try {
    await updateProfileApi(form);
    ElMessage.success('修改成功');
    dialogVisible.value = false;
    getProfile(); // 刷新页面数据
  } catch (error) {
    console.error(error);
  }
};
onMounted(() => {
  getProfile();
});
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>