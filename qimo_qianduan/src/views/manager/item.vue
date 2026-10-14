<template>
  <div>
    <el-card header="物品借还管理">
      <el-tabs v-model="activeTab" @tab-click="handleTabClick">

        <!-- Tab 1: 借用记录 (处理归还) -->
        <el-tab-pane label="借还记录" name="records">
          <el-button @click="loadRecords" icon="Refresh" circle aria-label="刷新" style="margin-bottom:10px"></el-button>
          <el-table :data="recordList" border height="500">
            <el-table-column label="借用人信息" min-width="180">
              <template #default="{ row }">
                <div style="line-height: 1.4;">
                  <span style="font-weight: bold;">{{ row.studentName }}</span>
                  <br/>
                  <span style="font-size: 12px; color: var(--color-text-muted);">
        {{ row.studentNo }} | {{ row.className }}
      </span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="itemName" label="物品" width="120" />
            <el-table-column prop="borrowTime" label="借出时间" width="180" />
            <el-table-column label="状态" width="100">
              <template #default="{ row }">
                <el-tag :type="row.status === 0 ? 'warning' : 'info'">
                  {{ row.status === 0 ? '未还' : '已还' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template #default="{ row }">
                <el-button
                    v-if="row.status === 0"
                    type="success"
                    size="small"
                    @click="handleReturn(row)"
                >确认归还</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <!-- Tab 2: 物品库管理 (增删改) -->
        <el-tab-pane label="物品库管理" name="stock">
          <el-button type="primary" @click="openDialog()">新增物品</el-button>
          <el-table :data="itemList" style="margin-top: 10px" border>
            <el-table-column prop="itemId" label="ID" width="60" />
            <el-table-column prop="name" label="名称" />
            <el-table-column prop="totalCount" label="总库存" />
            <el-table-column prop="availableCount" label="当前可用" />
            <el-table-column prop="location" label="位置" />
            <el-table-column label="操作">
              <template #default="{ row }">
                <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

      </el-tabs>
    </el-card>

    <!-- 新增弹窗 -->
    <el-dialog v-model="visible" title="新增物品">
      <el-form :model="form" label-width="80px">
        <el-form-item label="物品名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="总库存"><el-input-number v-model="form.totalCount" :min="1" /></el-form-item>
        <el-form-item label="存放位置"><el-input v-model="form.location" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="submitItem" type="primary">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { getItemListApi, addItemApi, deleteItemApi, getRecordListApi, returnItemApi } from '../../api/item';
import { ElMessage, ElMessageBox } from 'element-plus';

const activeTab = ref('records');
const itemList = ref([]);
const recordList = ref([]);
const visible = ref(false);
const form = reactive({ name: '', totalCount: 1, location: '' });

const loadItems = async () => itemList.value = await getItemListApi();
const loadRecords = async () => recordList.value = await getRecordListApi();

const handleTabClick = (tab) => {
  if (tab.paneName === 'stock') loadItems();
  if (tab.paneName === 'records') loadRecords();
};

const handleReturn = (row) => {
  ElMessageBox.confirm(`确认 ${row.studentName} 已归还 ${row.itemName}？`, '归还确认').then(async () => {
    await returnItemApi(row.recordId, row.itemId);
    ElMessage.success('操作成功');
    loadRecords();
  });
};

const openDialog = () => {
  form.name = ''; form.totalCount = 1; form.location = '';
  visible.value = true;
};

const submitItem = async () => {
  form.availableCount = form.totalCount; // 初始可用=总数
  await addItemApi(form);
  ElMessage.success('添加成功');
  visible.value = false;
  loadItems();
};

const handleDelete = (row) => {
  ElMessageBox.confirm('确定删除吗？').then(async () => {
    await deleteItemApi(row.itemId);
    ElMessage.success('删除成功');
    loadItems();
  });
};

onMounted(() => loadRecords());
</script>