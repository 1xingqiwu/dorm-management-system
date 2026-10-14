<template>
  <div class="payment-container">
    <!-- 余额卡片 -->
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="box-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span>当前电费余额</span>
            </div>
          </template>
          <div class="balance-text">
            ¥ {{ balance }}
          </div>
          <div class="action-box">
            <el-input-number v-model="amount" :min="10" :step="10" />
            <el-button type="success" @click="handleRecharge" :loading="loading">立即充值</el-button>
          </div>
        </el-card>
      </el-col>

      <!-- 模拟微信支付二维码 (纯展示) -->
      <el-col :span="8">
        <el-card shadow="hover">
          <div style="text-align:center; padding: 20px 0;">
            <el-icon :size="50" color="#09bb07"><Money /></el-icon>
            <p>支持微信/支付宝扫码</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 历史记录 -->
    <el-card style="margin-top: 20px;">
      <template #header>充值记录</template>
      <el-table :data="tableData" stripe>
        <el-table-column prop="createTime" label="充值时间" />
        <el-table-column prop="amount" label="金额">
          <template #default="{ row }">+{{ row.amount }}</template>
        </el-table-column>
        <el-table-column prop="balanceAfter" label="充值后余额" />
        <el-table-column prop="operatorName" label="操作人" />
        <el-table-column prop="paymentMethod" label="方式">
          <template #default><el-tag type="success">微信</el-tag></template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import request from '../../utils/request';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Money } from '@element-plus/icons-vue'; // 记得引入
import { rechargeApi, getPaymentInfoApi } from '../../api/payment'; // 引入 API

const balance = ref(0.00);
const amount = ref(50);
const loading = ref(false);
const tableData = ref([]);

// 获取数据 (余额+记录
const initData = async () => {
  try {
    const res = await getPaymentInfoApi();
    balance.value = res.balance; // 后端返回的真实余额
    tableData.value = res.logs;  // 后端返回的真实记录
  } catch (e) {
    console.error(e);
  }
};

const handleRecharge = () => {
  ElMessageBox.confirm(`确定要充值 ${amount.value} 元吗？`, '支付确认', {
    confirmButtonText: '确定支付',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    loading.value = true;
    try {
      await rechargeApi(amount.value); // 调用真实充值
      ElMessage.success('充值成功！');
      initData(); // 刷新余额和记录
    } finally {
      loading.value = false;
    }
  });
};


onMounted(() => initData());
</script>

<style scoped>
.balance-text { font-size: 32px; font-weight: bold; color: #f56c6c; margin-bottom: 20px; }
.action-box { display: flex; gap: 10px; }
</style>