<template>

  <div class="dashboard-container">
    <!-- 欢迎卡片 -->
    <el-card class="welcome-card">
      <div class="welcome-box">
        <el-avatar :size="60" src="../../../public/OIP.jpg" />
        <div class="text">
          <h2>张老师，下午好！</h2>
          <p>今天也要元气满满哦 ~</p>
        </div>
      </div>
    </el-card>

    <!-- 图表区域 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header><span>学生状态分布</span></template>
          <!-- ❗ 图表容器，必须给高度 -->
          <div ref="chartRef" style="width: 100%; height: 350px;"></div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card shadow="hover">
          <template #header><span>快捷入口</span></template>
          <div class="shortcut-box">
            <el-button type="primary" icon="User" circle size="large" aria-label="学生管理" @click="$router.push('/counselor/students')" />
            <p>学生管理</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import * as echarts from 'echarts'; // 引入 echarts

const chartRef = ref(null); // 对应 DOM 元素
let myChart = null;         // Chart 实例

const initChart = () => {
  // 1. 初始化
  myChart = echarts.init(chartRef.value);

  // 2. 配置项 (这里是假数据，真实开发需调用后端 API)
  const option = {
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        name: '学生状态',
        type: 'pie',
        radius: ['40%', '70%'], // 甜甜圈形状
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 20,
            fontWeight: 'bold'
          }
        },
        data: [
          { value: 45, name: '在校' },
          { value: 3, name: '请假' },
          { value: 2, name: '实习' },
          { value: 1, name: '休学' }
        ]
      }
    ]
  };

  // 3. 渲染
  myChart.setOption(option);
};

// 监听窗口大小变化，图表自适应
const handleResize = () => {
  myChart && myChart.resize();
};

onMounted(() => {
  initChart();
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
  if (myChart) myChart.dispose(); // 销毁实例防止内存泄漏
});
</script>

<style scoped>
.welcome-box { display: flex; align-items: center; gap: 20px; }
.welcome-box h2 { margin: 0 0 5px 0; color: #333; }
.welcome-box p { margin: 0; color: var(--color-text-muted); }
.shortcut-box { text-align: center; display: inline-block; margin-right: 30px; cursor: pointer; }
</style>