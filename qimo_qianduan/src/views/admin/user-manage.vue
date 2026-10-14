<template>
  <div class="user-manage">
    <el-card>
      <!-- 1. 搜索栏 -->
      <template #header>
        <div class="filter-box">
          <el-input v-model="queryParams.username" placeholder="请输入账号/姓名" style="width: 200px;" clearable @clear="getList" />
          <el-select v-model="queryParams.userType" placeholder="角色" style="width: 150px;" clearable @clear="getList">
            <el-option label="学生" :value="0" />
            <el-option label="辅导员" :value="1" />
            <el-option label="宿管" :value="2" />
            <el-option label="管理员" :value="3" />
          </el-select>
          <el-button type="primary" @click="getList">
            <el-icon><Search /></el-icon> 查询
          </el-button>
          <!-- 仅超级管理员可见的「新增账号」入口 -->
          <el-button v-if="isAdmin" type="success" icon="Plus" style="margin-left: auto;" @click="openCreate">
            新增账号
          </el-button>
        </div>
      </template>

      <!-- 2. 表格区域 -->
      <el-table :data="tableData" border stripe v-loading="loading">
        <el-table-column prop="username" label="账号" width="150" sortable />
        <el-table-column prop="nickname" label="姓名" width="150" />
        <el-table-column prop="userType" label="角色" width="120">
          <template #default="{ row }">
            <el-tag :type="getRoleTag(row.userType)">{{ getRoleName(row.userType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="150" />
        <el-table-column prop="status" label="是否禁用" width="100">
          <template #default="{ row }">
            <el-switch
                v-model="row.status"
                :active-value="1"
                :inactive-value="0"
                aria-label="是否禁用"
                @change="handleStatusChange(row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />

        <el-table-column label="操作" min-width="250">
          <template #default="{ row }">
            <el-button type="primary" size="small" icon="Edit" @click="openEdit(row)">编辑</el-button>
            <el-button type="warning" size="small" icon="RefreshLeft" @click="handleResetPwd(row)">重置密码</el-button>
            <el-popconfirm title="确定要删除该账号吗？此操作不可恢复！" @confirm="handleDelete(row)">
              <template #reference>
                <el-button type="danger" size="small" icon="Delete">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 3. 新增账号弹窗（仅超级管理员） -->
    <el-dialog v-model="createVisible" title="新增账号" width="520px" :close-on-click-modal="false">
      <el-form
          ref="createFormRef"
          :model="createForm"
          :rules="createRules"
          label-width="90px"
      >
        <el-form-item label="账号" prop="username">
          <el-input v-model="createForm.username" placeholder="3-20位字母、数字或下划线" clearable />
        </el-form-item>
        <el-form-item label="姓名" prop="nickname">
          <el-input v-model="createForm.nickname" placeholder="请输入姓名" clearable />
        </el-form-item>
        <el-form-item label="角色" prop="userType">
          <el-select v-model="createForm.userType" placeholder="请选择角色" style="width: 100%;">
            <el-option label="学生" :value="0" />
            <el-option label="辅导员" :value="1" />
            <el-option label="宿管" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="createForm.password" type="password" show-password placeholder="6-32位密码" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="createForm.phone" placeholder="请输入手机号" clearable />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="createForm.email" placeholder="选填，用于找回密码" clearable />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="createVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="submitCreate">提交</el-button>
      </template>
    </el-dialog>

    <!-- 4. 编辑弹窗 -->
    <el-dialog v-model="dialogVisible" title="编辑用户信息" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="账号">
          <el-input v-model="form.username" disabled />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitEdit">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="resetVisible" title="重置密码" width="400px">
      <el-form :model="resetForm" label-width="80px">
        <el-form-item label="目标账号">
          <el-input v-model="resetForm.username" disabled />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input
              v-model="resetForm.newPassword"
              type="password"
              show-password
              placeholder="请输入新密码"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="resetVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReset">确认重置</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue';
import { createUserApi, getUserListApi, updateUserApi, changeUserStatusApi, deleteUserApi, resetUserPwdApi } from '../../api/user';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Search, Edit, Delete, RefreshLeft, Plus } from '@element-plus/icons-vue';
import { useUserStore } from '../../store/user';

const userStore = useUserStore();
// 仅超级管理员(userType=3)可见并可使用「新增账号」功能
const isAdmin = computed(() => userStore.userType === 3);

const loading = ref(false);
const tableData = ref([]);
const dialogVisible = ref(false);

const queryParams = reactive({
  username: '',
  userType: null
});

const form = reactive({ userId: null, username: '', nickname: '', phone: '', email: '' });

// 获取列表
const getList = async () => {
  loading.value = true;
  try {
    const res = await getUserListApi(queryParams);
    tableData.value = res;
  } finally {
    loading.value = false;
  }
};

// 状态修改
const handleStatusChange = async (row) => {
  try {
    await changeUserStatusApi(row.userId, row.status);
    ElMessage.success('状态已更新');
  } catch (e) {
    row.status = row.status === 1 ? 0 : 1; // 回滚
  }
};

// 打开编辑
const openEdit = (row) => {
  Object.assign(form, row);
  dialogVisible.value = true;
};

// 提交编辑
const submitEdit = async () => {
  await updateUserApi(form);
  ElMessage.success('保存成功');
  dialogVisible.value = false;
  getList();
};

// 删除
const handleDelete = async (row) => {
  await deleteUserApi(row.userId);
  ElMessage.success('删除成功');
  getList();
};


const resetVisible = ref(false);
const resetForm = reactive({
  userId: null,
  username: '',
  newPassword: ''
});


// 重置密码
const handleResetPwd = (row) => {
  resetForm.userId = row.userId;
  resetForm.username = row.username;
  resetForm.newPassword = ''; // 清空之前的输入
  resetVisible.value = true;
};

const submitReset = async () => {
  if (!resetForm.newPassword || resetForm.newPassword.length < 6) {
    ElMessage.warning('请输入至少6位的新密码');
    return;
  }

  try {
    // 调用 API，传入 ID 和 新密码
    await resetUserPwdApi(resetForm.userId, resetForm.newPassword);
    ElMessage.success(`账号 ${resetForm.username} 密码重置成功`);
    resetVisible.value = false;
  } catch (e) {
    console.error(e);
  }
};

// ===== 新增账号 =====
const createVisible = ref(false);
const submitting = ref(false);
const createFormRef = ref(null);
const createForm = reactive({
  username: '',
  nickname: '',
  userType: null,
  password: '',
  phone: '',
  email: ''
});

// 前端校验规则：与后端 CreateUserDTO 的 JSR-303 约束保持一致
const createRules = {
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { pattern: /^[A-Za-z0-9_]{3,20}$/, message: '账号需为3-20位字母、数字或下划线', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { max: 64, message: '姓名长度不能超过64位', trigger: 'blur' }
  ],
  userType: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 32, message: '密码长度需为6-32位', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ]
};

// 打开新增弹窗，重置表单与校验状态
const openCreate = () => {
  createFormRef.value?.resetFields();
  Object.assign(createForm, {
    username: '', nickname: '', userType: null, password: '', phone: '', email: ''
  });
  createVisible.value = true;
};

// 提交新增
const submitCreate = async () => {
  if (!createFormRef.value) return;
  // 1. 表单合法性校验（必填/格式）
  const valid = await createFormRef.value.validate().catch(() => false);
  if (!valid) {
    ElMessage.warning('请检查表单填写是否正确');
    return;
  }
  submitting.value = true;
  try {
    // 2. 调用后端接口（唯一性校验在后端完成，重复账号由后端拦截返回错误）
    await createUserApi({ ...createForm });
    ElMessage.success('账号创建成功');
    createVisible.value = false;
    getList();
  } catch (e) {
    // 3. 失败反馈：request 拦截器已统一弹出后端错误提示，此处仅兜底
    if (!e?.response) {
      ElMessage.error(e?.message || '账号创建失败');
    }
  } finally {
    submitting.value = false;
  }
};

// 工具函数
const getRoleName = (type) => ['学生', '辅导员', '宿管', '管理员'][type] || '未知';
const getRoleTag = (type) => ['success', 'warning', 'info', 'danger'][type] || 'info';

onMounted(() => getList());
</script>

<style scoped>
.filter-box { display: flex; align-items: center; flex-wrap: wrap; gap: 10px; }
</style>