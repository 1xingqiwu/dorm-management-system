<template>
  <div class="dashboard-container">
    <el-card class="welcome-card">
      <h2>宿管阿姨，欢迎回来！</h2>
      <p>您管理的楼栋：<strong>南苑1号楼</strong></p>
    </el-card>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header><span>本月报修类型统计</span></template>
          <div ref="barChartRef" style="width: 100%; height: 400px;"></div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card shadow="hover">
          <template #header><span>待办事项</span></template>
          <el-timeline>
            <el-timeline-item timestamp="2025-11-27" placement="top" type="danger">
              <el-card>
                <h4>新报修待处理</h4>
                <p>101宿舍 - 水管漏水</p>
              </el-card>
            </el-timeline-item>
            <el-timeline-item timestamp="2025-11-26" placement="top" type="primary">
              <el-card>
                <h4>新访客申请</h4>
                <p>王爸爸 - 探望王小明</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import * as echarts from 'echarts';

const barChartRef = ref(null);
let myChart = null;

const initChart = () => {
  myChart = echarts.init(barChartRef.value);

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: ['水电维修', '家具维修', '网络维修', '门窗维修', '其他'],
        axisTick: { alignWithLabel: true }
      }
    ],
    yAxis: [
      { type: 'value' }
    ],
    series: [
      {
        name: '报修数量',
        type: 'bar',
        barWidth: '60%',
        data: [
          { value: 10, itemStyle: { color: '#5470c6' } },
          { value: 5, itemStyle: { color: '#91cc75' } },
          { value: 8, itemStyle: { color: '#fac858' } },
          { value: 2, itemStyle: { color: '#ee6666' } },
          { value: 4, itemStyle: { color: '#73c0de' } }
        ]
      }
    ]
  };

  myChart.setOption(option);
};

const handleResize = () => myChart && myChart.resize();

onMounted(() => {
  initChart();
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
  if (myChart) myChart.dispose();
});
</script>