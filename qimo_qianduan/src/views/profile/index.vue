<template>
  <div class="profile-container">
    <el-row :gutter="20">
      <!-- 左侧：个人卡片 -->
      <el-col :span="8">
        <el-card class="box-card">
          <div class="user-header">
            <el-avatar :size="100" src="../../../public/OIP.jpg" />
            <h3>{{ userInfo.nickname }}</h3>
            <p>{{ roleName }}</p>
          </div>
          <el-divider />
          <div class="user-info">
            <p><el-icon><User /></el-icon> 账号：{{ userInfo.username }}</p>
            <p><el-icon><Iphone /></el-icon> 手机：{{ userInfo.phone || '未绑定' }}</p>
            <p><el-icon><Message /></el-icon> 邮箱：{{ userInfo.email || '未绑定' }}</p>
          </div>
        </el-card>
      </el-col>

      <!-- 右侧：编辑表单 -->
      <el-col :span="16">
        <el-card>
          <el-tabs v-model="activeTab">

            <!-- Tab 1: 修改资料 -->
            <el-tab-pane label="基本资料" name="info">
              <el-form :model="infoForm" label-width="80px" style="margin-top: 20px;">
                <el-form-item label="昵称">
                  <el-input v-model="infoForm.nickname" />
                </el-form-item>
                <el-form-item label="手机号">
                  <el-input v-model="infoForm.phone" />
                </el-form-item>
                <el-form-item label="邮箱">
                  <el-input v-model="infoForm.email" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="handleUpdateInfo">保存修改</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>

            <!-- Tab 2: 修改密码 -->
            <el-tab-pane label="修改密码" name="password">
              <el-form :model="pwdForm" :rules="pwdRules" ref="pwdFormRef" label-width="100px" style="margin-top: 20px;">
                <el-form-item label="旧密码" prop="oldPassword">
                  <el-input v-model="pwdForm.oldPassword" type="password" show-password />
                </el-form-item>
                <el-form-item label="新密码" prop="newPassword">
                  <el-input v-model="pwdForm.newPassword" type="password" show-password />
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                  <el-input v-model="pwdForm.confirmPassword" type="password" show-password />
                </el-form-item>
                <el-form-item>
                  <el-button type="danger" @click="handleUpdatePwd">确认修改</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>

          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { useUserStore } from '../../store/user';
import { updateProfileApi, updatePasswordApi, getUserInfoApi } from '../../api/auth';
import { ElMessage } from 'element-plus';
import { User, Iphone, Message } from '@element-plus/icons-vue';

const userStore = useUserStore();
const activeTab = ref('info');
const userInfo = ref({});

// 角色名映射
const roleName = computed(() => {
  const roles = {0: '学生', 1: '辅导员', 2: '宿管', 3: '管理员'};
  return roles[userStore.userType] || '未知角色';
});

// 表单数据
const infoForm = reactive({ nickname: '', phone: '', email: '' });
const pwdForm = reactive({ oldPassword: '', newPassword: '', confirmPassword: '' });
const pwdFormRef = ref(null);

// 密码校验规则
const validatePass2 = (rule, value, callback) => {
  if (value !== pwdForm.newPassword) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};
const pwdRules = {
  oldPassword: [{ required: true, message: '请输入旧密码', trigger: 'blur' }],
  newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }, { min: 6, message: '长度不能少于6位', trigger: 'blur' }],
  confirmPassword: [{ required: true, validator: validatePass2, trigger: 'blur' }]
};

// 加载数据
const loadData = async () => {
  // 重新调一下后端获取最新信息
  const res = await getUserInfoApi();
  userInfo.value = res;
  // 回填表单
  infoForm.nickname = res.nickname;
  infoForm.phone = res.phone;
  infoForm.email = res.email;
  // 同步到 store
  userStore.setUserInfo(res, userStore.userType);
};

// 修改资料
const handleUpdateInfo = async () => {
  await updateProfileApi(infoForm);
  ElMessage.success('资料保存成功');
  loadData(); // 刷新
};

// 修改密码
const handleUpdatePwd = () => {
  pwdFormRef.value.validate(async (valid) => {
    if (valid) {
      await updatePasswordApi({
        oldPassword: pwdForm.oldPassword,
        newPassword: pwdForm.newPassword
      });
      ElMessage.success('密码修改成功，请重新登录');
      userStore.logout(); // 强制登出
    }
  });
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.user-header { text-align: center; margin-bottom: 20px; }
.user-header h3 { margin: 10px 0 5px; }
.user-header p { color: var(--color-text-muted); font-size: 14px; margin: 0; }
.user-info p { margin: 15px 0; font-size: 14px; color: #606266; display: flex; align-items: center; }
.user-info .el-icon { margin-right: 8px; font-size: 16px; }
</style>