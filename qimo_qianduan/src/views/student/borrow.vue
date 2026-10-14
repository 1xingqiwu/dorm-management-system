<template>
  <div class="borrow-container">
    <el-card>
      <el-tabs v-model="activeTab" @tab-click="handleTabClick">

        <!-- Tab 1: 物品列表 -->
        <el-tab-pane label="可借物品" name="items">
          <el-table :data="itemList" stripe border>
            <el-table-column prop="name" label="物品名称" />
            <el-table-column prop="location" label="存放位置" />
            <el-table-column label="库存状态">
              <template #default="{ row }">
                <el-tag :type="row.availableCount > 0 ? 'success' : 'danger'">
                  剩余: {{ row.availableCount }} / {{ row.totalCount }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
              <template #default="{ row }">
                <el-button
                    type="primary"
                    size="small"
                    :disabled="row.availableCount <= 0"
                    @click="handleBorrow(row)"
                >借用</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- Tab 2: 借用记录 -->
        <el-tab-pane label="我的借用" name="records">
          <el-table :data="recordList" stripe border>
            <el-table-column prop="itemName" label="物品名称" />
            <el-table-column prop="borrowTime" label="借用时间" />
            <el-table-column prop="returnTime" label="归还时间" />
            <el-table-column label="状态">
              <template #default="{ row }">
                <el-tag :type="row.status === 0 ? 'warning' : 'info'">
                  {{ row.status === 0 ? '使用中' : '已归还' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getItemListApi, borrowItemApi, getRecordListApi } from '../../api/item';
import { ElMessage, ElMessageBox } from 'element-plus';

const activeTab = ref('items');
const itemList = ref([]);
const recordList = ref([]);

const loadItems = async () => {
  itemList.value = await getItemListApi();
};

const loadRecords = async () => {
  recordList.value = await getRecordListApi();
};

const handleTabClick = (tab) => {
  if (tab.paneName === 'items') loadItems();
  if (tab.paneName === 'records') loadRecords();
};

const handleBorrow = (row) => {
  ElMessageBox.confirm(`确定要借用 ${row.name} 吗？`, '提示').then(async () => {
    await borrowItemApi(row.itemId);
    ElMessage.success('借用成功');
    loadItems(); // 刷新库存
  });
};

onMounted(() => {
  loadItems();
});
</script>