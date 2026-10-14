<template>
  <div class="system-settings">
    <el-card header="系统参数设置">
      <el-form label-width="200px">
        <el-form-item label="开启用户注册功能">
          <el-switch
              v-model="registerEnabled"
              active-text="开启"
              inactive-text="关闭"
              @change="handleChange"
          />
          <div class="tips">关闭后，登录页将隐藏注册入口，且无法调用注册接口。</div>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getRegisterConfigApi, updateRegisterConfigApi } from '../../api/system';
import { ElMessage } from 'element-plus';

const registerEnabled = ref(true);

const loadConfig = async () => {
  const res = await getRegisterConfigApi();
  registerEnabled.value = res;
};

const handleChange = async (val) => {
  try {
    await updateRegisterConfigApi(val);
    ElMessage.success('设置已保存');
  } catch (e) {
    // 失败回滚
    registerEnabled.value = !val;
  }
};

onMounted(() => loadConfig());
</script>

<style scoped>
.tips { margin-left: 10px; color: var(--color-text-muted); font-size: 12px; }
</style>