<template>
  <div>
    <!--搜索-->
    <div style="float: left">
      <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item>
          <el-input v-model.trim="params.username" @keyup.enter="query" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="query">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="add">新增</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!--表格数据-->
    <el-table ref="multipleTable" :data="page.list" tooltip-effect="dark" style="width: 100%">
      <el-table-column type="selection">
      </el-table-column>
      <el-table-column prop="userId" label="用户id">
      </el-table-column>
      <el-table-column prop="username" label="用户名">
      </el-table-column>
      <el-table-column prop="chineseName" label="姓名">
      </el-table-column>
      <el-table-column prop="roleName" label="userType">
        <template #default="scope">
          <el-tag type="success" size="small" v-if="scope.row.userType === 1">普通用户</el-tag>
          <el-tag type="success" size="small" v-if="scope.row.userType === 2">员工</el-tag>
          <el-tag type="success" size="small" v-if="scope.row.userType === 3">管理员</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phoneNumber" label="手机号">
      </el-table-column>
      <el-table-column prop="" label="操作">
        <template #default="scope">
          <el-button type="primary" size="small" circle @click="update(scope.row.userId)">
            <el-icon>
              <Edit />
            </el-icon>
          </el-button>
          <el-button type="danger" size="small" circle @click="del(scope.row.userId)">
            <el-icon>
              <Delete />
            </el-icon>
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 20px 30%">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="page.currPage"
        :page-sizes="[1, 5, 10, 15, 20]" :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="page.totalCount" style="text-align: center">
      </el-pagination>
    </div>

  </div>


  <el-dialog v-model="showInfo">
    <el-form :model="user" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username" auto-complete="new-password"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="chineseName">
        <el-input v-model="user.chineseName" auto-complete="new-password"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="user.password" auto-complete="new-password" show-password></el-input>
      </el-form-item>
      <el-form-item label="用户类型" prop="userType">
        <el-radio-group v-model="user.userType">
          <el-radio v-for="role in userTypeList" v-bind:key="role.id" :label="role.id">{{ role.name }}</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="saveOrUpdate(ruleForm)">确定</el-button>
        <el-button @click="showInfo = false">返回</el-button>
      </span>
    </template>

  </el-dialog>
</template>

<script setup lang="ts">
import { listUser, userInfo, saveUser, updateUser, deleteUser } from "@/api/user";
import { ElMessage, ElMessageBox, FormRules, FormInstance } from "element-plus";
import { ref, reactive, onMounted } from 'vue'


let user = reactive({
  userId: null,
  username: '',
  password: '',
  phoneNumber: '',
  chineseName: '',
  userType: ''
})
let params = reactive({
  username: '',
  page: '1',
  limit: '10'
})
const page = ref({
  currPage: 1,
  pageSize: 10,
  totalCount: 0,
  list: []
})
const userTypeList = [
  {
    id: 1,
    name: '用户'
  },
  {
    id: 2,
    name: '管理员'
  },
]
const ruleForm = ref<FormInstance>()
const rules = reactive<FormRules>({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, message: '密码最少3位', trigger: 'blur' }
  ],
  userType: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
})
let showInfo = ref<boolean>(false)

const loadPage = () => {
  listUser(params).then((res: any) => {
    if (res.code === 200) {
      page.value = res.page
    } else {
      ElMessage({
        message: res.msg,
        type: 'error'
      })
    }
  })
}
const info = (userId: number) => {
  userInfo(userId).then(res => {
    user = res.user
  })
}
const query = () => {
  loadPage()
}

const handleSizeChange = (val: number) => {
  params.limit = val.toString()
  loadPage()
}
const handleCurrentChange = (val: number) => {
  params.page = val.toString()
  loadPage()
}
const add = () => {
  user = {
    userId: null,
    username: '',
    password: '',
    phoneNumber: '',
    chineseName: '',
    userType: ''
  }
  showInfo.value = true
}
const update = (userId: number) => {
  info(userId)
  showInfo.value = true
}
const del = (userId: number) => {
  ElMessageBox.confirm(
    '是否删除选中的数据?',
    'Warning',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    deleteUser(userId).then(res => {
      if (res.code === 200) {
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
        loadPage()
      } else {
        ElMessage({
          type: 'error',
          message: res.msg,
        })
      }
    })
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消删除',
    })
  })
}
const saveOrUpdate = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      if (user.userId == null) {
        saveUser(user).then(res => {
          if (res.code === 200) {
            ElMessage({
              message: 'success',
              type: 'success'
            })
            loadPage()
          } else {
            ElMessage({
              message: res.msg,
              type: 'error'
            })
          }
        })
      } else {
        updateUser(user).then(res => {
          if (res.code === 200) {
            ElMessage({
              message: 'success',
              type: 'success'
            })
            loadPage()
          } else {
            ElMessage({
              message: res.msg,
              type: 'error'
            })
          }
        })
      }
    }
  })
}

onMounted(() => {
  loadPage()
})


</script>

<style scoped></style>