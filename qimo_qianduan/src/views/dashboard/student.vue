<template>
  <div class="student-dashboard">
    <!-- 1. 顶部欢迎卡片 -->
    <el-card class="welcome-card" shadow="never">
      <div class="welcome-content">
        <el-avatar :size="70" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" class="avatar"/>
        <div class="text-content">
          <h2 class="greet">{{ timeGreet }}，{{ userStore.userInfo.nickname || userStore.userInfo.username }}</h2>
          <p class="subtitle">今天也要保持好心情，努力学习哦！📅 {{ currentDate }}</p>
        </div>
      </div>
    </el-card>

    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 左侧：数据概览 + 快捷入口 -->
      <el-col :span="16">
        <!-- 2. 数据统计卡片 -->
        <el-row :gutter="20">
          <el-col :span="8">
            <el-card shadow="hover" class="data-card electricity">
              <div class="card-header">
                <el-icon><Lightning /></el-icon>
                <span>电费余额</span>
              </div>
              <div class="card-num">
                ¥ {{ balance }}
              </div>
              <el-button type="primary" link size="small" @click="$router.push('/student/payment')">去充值 ></el-button>
            </el-card>
          </el-col>

          <el-col :span="8">
            <el-card shadow="hover" class="data-card hygiene">
              <div class="card-header">
                <el-icon><Medal /></el-icon>
                <span>上周卫生分</span>
              </div>
              <div class="card-num" :style="{color: hygieneScore >= 90 ? '#67C23A' : '#E6A23C'}">
                {{ hygieneScore || '-' }}
              </div>
              <div class="card-footer">排名：全楼第 5</div>
            </el-card>
          </el-col>

          <el-col :span="8">
            <el-card shadow="hover" class="data-card repair">
              <div class="card-header">
                <el-icon><Tools /></el-icon>
                <span>报修状态</span>
              </div>
              <div class="card-num">
                {{ repairCount }} <span style="font-size: 14px; color: var(--color-text-muted)">单进行中</span>
              </div>
              <el-button type="primary" link size="small" @click="$router.push('/student/repair')">查看进度 ></el-button>
            </el-card>
          </el-col>
        </el-row>

        <!-- 3. 快捷入口 -->
        <el-card shadow="hover" style="margin-top: 20px;" header="快捷导航">
          <div class="shortcut-grid">
            <div class="shortcut-item" @click="$router.push('/student/leave')">
              <div class="icon-box bg-blue"><el-icon><Position /></el-icon></div>
              <span>请假申请</span>
            </div>
            <div class="shortcut-item" @click="$router.push('/student/repair')">
              <div class="icon-box bg-orange"><el-icon><Tools /></el-icon></div>
              <span>故障报修</span>
            </div>
            <div class="shortcut-item" @click="$router.push('/student/borrow')">
              <div class="icon-box bg-green"><el-icon><Box /></el-icon></div>
              <span>物品借用</span>
            </div>
            <div class="shortcut-item" @click="$router.push('/student/payment')">
              <div class="icon-box bg-purple"><el-icon><Money /></el-icon></div>
              <span>生活缴费</span>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 右侧：最新公告 -->
      <el-col :span="8">
        <el-card shadow="hover" class="notice-card">
          <template #header>
            <div class="card-header-flex">
              <span>🔔 最新公告</span>
              <el-button link type="primary" @click="$router.push('/student/notice')">更多</el-button>
            </div>
          </template>

          <div v-if="noticeList.length === 0" class="empty-notice">暂无公告</div>
          <ul v-else class="notice-list">
            <li v-for="item in noticeList" :key="item.noticeId" @click="$router.push('/student/notice')">
              <div class="notice-title">{{ item.title }}</div>
              <div class="notice-time">{{ formatTime(item.createTime) }}</div>
            </li>
          </ul>
        </el-card>

        <!-- 装饰性图片或标语 -->
        <el-card shadow="hover" style="margin-top: 20px;">
          <div class="illustration">
            <img src="https://cdnuploads.aa.com/2021/06/Customer-Service_AA-1.jpg" style="width: 100%; border-radius: 8px;" alt="banner"/>
            <p style="text-align: center; color: #666; margin-top: 10px;">构建和谐校园，从我做起</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useUserStore } from '../../store/user';
import { getPaymentInfoApi } from '../../api/payment';
import { getNoticeListApi } from '../../api/notice';
import { getStudentProfileApi } from '../../api/student'; // 假设你有获取个人信息的API
import { Lightning, Medal, Tools, Position, Box, Money } from '@element-plus/icons-vue';
import dayjs from 'dayjs';

const userStore = useUserStore();
const balance = ref(0.00);
const hygieneScore = ref(0);
const repairCount = ref(0); // 这里可以调接口查未完成的报修
const noticeList = ref([]);

// 1. 计算问候语
const timeGreet = computed(() => {
  const hour = new Date().getHours();
  if (hour < 6) return '凌晨好';
  if (hour < 9) return '早上好';
  if (hour < 12) return '上午好';
  if (hour < 14) return '中午好';
  if (hour < 17) return '下午好';
  if (hour < 19) return '傍晚好';
  return '晚上好';
});

// 2. 当前日期
const currentDate = dayjs().format('YYYY年MM月DD日 星期d');

// 3. 格式化时间
const formatTime = (time) => dayjs(time).format('MM-DD');

// 4. 加载数据
const initData = async () => {
  try {
    // 获取余额 (复用之前的API)
    const payRes = await getPaymentInfoApi();
    balance.value = payRes.balance;

    // 获取公告 (只取前5条)
    const noticeRes = await getNoticeListApi();
    noticeList.value = noticeRes.slice(0, 5);

    // 这里为了演示效果，卫生分先写死或调接口
    // const profile = await getStudentProfileApi();
    // 假设后端有接口查最近一次卫生分
    hygieneScore.value = 95;
    repairCount.value = 1;

  } catch (e) {
    console.error(e);
  }
};

onMounted(() => {
  initData();
});
</script>

<style scoped lang="scss">
.student-dashboard {
  .welcome-card {
    background: linear-gradient(135deg, #ffffff 0%, #f0f9ff 100%);
    .welcome-content {
      display: flex;
      align-items: center;
      gap: 20px;
      .greet { margin: 0 0 8px 0; color: #303133; }
      .subtitle { margin: 0; color: #909399; font-size: 14px; }
    }
  }

  .data-card {
    height: 140px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    .card-header {
      display: flex;
      align-items: center;
      gap: 8px;
      color: #606266;
      font-size: 14px;
    }
    .card-num {
      font-size: 28px;
      font-weight: bold;
      color: #303133;
      margin: 10px 0;
    }
    .card-footer {
      font-size: 12px;
      color: var(--color-text-muted);
    }

    &.electricity .card-header { color: #E6A23C; }
    &.hygiene .card-header { color: #67C23A; }
    &.repair .card-header { color: var(--color-primary); }
  }

  .shortcut-grid {
    display: flex;
    justify-content: space-around;
    padding: 10px 0;

    .shortcut-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      cursor: pointer;
      transition: transform 0.2s;

      &:hover { transform: translateY(-5px); }

      .icon-box {
        width: 50px;
        height: 50px;
        border-radius: 12px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 8px;
        color: #fff;
        font-size: 24px;

        &.bg-blue { background: var(--color-primary); box-shadow: 0 4px 10px rgba(64, 158, 255, 0.3); }
        &.bg-orange { background: #E6A23C; box-shadow: 0 4px 10px rgba(230, 162, 60, 0.3); }
        &.bg-green { background: #67C23A; box-shadow: 0 4px 10px rgba(103, 194, 58, 0.3); }
        &.bg-purple { background: #909399; box-shadow: 0 4px 10px rgba(144, 147, 153, 0.3); }
      }

      span { font-size: 14px; color: #606266; }
    }
  }

  .notice-card {
    min-height: 320px;
    .card-header-flex {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .notice-list {
      list-style: none;
      padding: 0;
      margin: 0;

      li {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 12px 0;
        border-bottom: 1px solid #f0f0f0;
        cursor: pointer;

        &:last-child { border-bottom: none; }
        &:hover { background-color: #fafafa; }

        .notice-title {
          flex: 1;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
          font-size: 14px;
          color: #303133;
          margin-right: 10px;
        }
        .notice-time {
          font-size: 12px;
          color: var(--color-text-muted);
          width: 40px;
          text-align: right;
        }
      }
    }
  }
}
</style>