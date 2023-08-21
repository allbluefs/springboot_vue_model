<template>
  <el-table :data="menuData" style="width: 100%
  margin-bottom: 20px" row-key="menuId" highlight-current-row>

    <el-table-column prop="menuId" label="ID" width="100px" />
    <el-table-column prop="name" label="菜单名称" />
    <el-table-column prop="parentName" label="上级菜单" />
    <el-table-column prop="" label="图标">
      <template #default="scope">
        <el-icon>
          <component :is="scope.row.icon"></component>
        </el-icon>
      </template>
    </el-table-column>
    <el-table-column prop="type" label="类型">
      <template #default="scope">
        <el-tag v-if="scope.row.type === 0">目录</el-tag>
        <el-tag v-if="scope.row.type === 1">菜单</el-tag>
        <el-tag v-if="scope.row.type === 2">按钮</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="perms" label="权限码" />
    <el-table-column prop="orderNum" label="排序号" />
    <el-table-column prop="path" label="菜单PATH" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" circle type="primary" @click="handleEdit(scope.$index, scope.row)">
          <el-icon>
            <Edit />
          </el-icon>
        </el-button>
        <el-button size="small" circle type="danger" @click="handleDelete(scope.$index, scope.row)">
          <el-icon>
            <Delete />
          </el-icon>
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { menuTree } from '@/api/menu';
interface MenuItem {
  menuId: number
  path: string
  type: number
  name: string
  parentName: string
  icon: string
  children?: MenuItem[]
}
const menuData = ref<MenuItem[]>()
onMounted(() => {
  menuTree().then(res => {
    menuData.value = res.data
  })
})

const handleEdit = (index: any, row: any) => {
  console.log(index, row)
}

const handleDelete = (index: any, row: any) => {
  console.log(index, row)
}


</script>
