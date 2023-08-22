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
      <template #header>
        <el-button size="small" circle type="primary" @click="handleAdd()">
          <el-icon>
            <Plus />
          </el-icon>
        </el-button>
      </template>
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

  <!-- 菜单详情 -->
  <el-dialog v-model="showMenuInfo">
    <el-form :model="menu" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="menu.type">
          <el-radio v-for="item in menuType" v-bind:key="item.id" :label="item.id">{{ item.name }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="菜单名称" prop="name">
        <el-input v-model="menu.name"></el-input>
      </el-form-item>
      <el-form-item label="上级菜单" prop="parentName">
        <el-col :span="12">
          <el-input disabled v-model.trim="menu.parentName"></el-input>
        </el-col>
        <el-col :span="12">
          <el-popover :visible="popoverVisible" placement="right" :width="400" trigger="click">
            <template #reference>
              <el-button style="margin-right: 16px" @click="popoverVisible = true">选择上级菜单</el-button>
            </template>
            <el-tree ref="treeRef" :data="selectMenuList" highlight-current node-key="menuId" :props="defaultProps"
              @node-click="handleNodeClick"></el-tree>
            <div style="text-align: right; margin: 0">
              <el-button size="small" text @click="popoverVisible = false">取消</el-button>
              <el-button size="small" type="primary" @click="setParentMenu">确定</el-button>
            </div>
          </el-popover>
        </el-col>
      </el-form-item>
      <el-form-item label="菜单URL" prop="url" v-if="menu.type === 1">
        <el-input v-model="menu.path"></el-input>
      </el-form-item>
      <el-form-item label="授权标志" prop="perms" v-if="menu.type === 1 || menu.type === 2">
        <el-input v-model="menu.perms" aria-placeholder="多个用逗号分隔，如：user:list,user:create"></el-input>
      </el-form-item>
      <el-form-item label="菜单图标" prop="icon" v-if="menu.type !== 2">
        <el-input v-model="menu.icon"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="orderNum" v-if="menu.type !== 2">
        <el-input v-model="menu.orderNum"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="showMenuInfo = false">返回</el-button>
        <el-button type="primary" @click="saveOrUpdate(ruleForm)">确定</el-button>
      </span>
    </template>

  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue';
import { menuTree, menuInfo, saveMenu, updateMenu, deleteMenu } from '@/api/menu';
import { ElMessage, ElMessageBox, FormRules, FormInstance, ElTree } from 'element-plus';
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
const getMenuTree = () => {
  menuTree().then(res => {
    menuData.value = res.data
  })
}
onMounted(() => {
  getMenuTree()
})
const handleAdd = () => {
  menuTree().then(res => {
    menu.value = {}
    selectMenuList.value[0].children = res.data
    console.log(selectMenuList.value)
    showMenuInfo.value = true
  })
}
const handleEdit = (index: any, row: any) => {
  menuInfo(row.menuId).then(res => {
    menu.value = res.data
    showMenuInfo.value = true
  })
}
const handleDelete = (index: any, row: any) => {
  console.log(index, row)
  ElMessageBox.confirm(
    '是否删除选中的数据?',
    'Warning',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    deleteMenu(row.menuId).then(res => {
      if (res.code === 200) {
        ElMessage.success('删除成功')
        getMenuTree()
      } else {
        ElMessage.error(res.msg)
      }
    })
  })

}
const menu = ref()
const menuType = [
  {
    id: 0,
    name: '目录'
  },
  {
    id: 1,
    name: '菜单'
  },
  {
    id: 2,
    name: '按钮'
  },
]
const showMenuInfo = ref(false)
const rules = reactive<FormRules>({

})
const ruleForm = ref<FormInstance>()
const saveOrUpdate = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  if (menu.value.menuId == null) {
    saveMenu(menu.value).then(res => {
      if (res.code === 200) {
        ElMessage.success('保存成功')
        showMenuInfo.value = false
        getMenuTree()
      } else {
        ElMessage.error(res.msg)
      }
    })
  } else {
    updateMenu(menu.value).then(res => {
      if (res.code === 200) {
        ElMessage.success('更新成功')
        showMenuInfo.value = false
        getMenuTree()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }
}
const defaultProps = {
  children: 'children',
  label: 'name',
}
const selectMenuList = ref([
  {
    menuId: 0,
    name: "一级菜单",
    children: []
  }
])
interface Tree {
  menuId: number
  name: string
  children?: Tree[]
}
const treeRef = ref<InstanceType<typeof ElTree>>()
const handleNodeClick = (data: Tree) => {
  console.log(data)
}
const setParentMenu = () => {
  console.log(treeRef.value)
  if (treeRef.value?.getCurrentNode() != null) {
    menu.value.parentName = treeRef.value?.getCurrentNode().name
    menu.value.parentId = treeRef.value?.getCurrentNode().menuId
  }
  popoverVisible.value = false
}
const popoverVisible = ref(false)




</script>

<style scoped></style>