<template>
  <el-row style="height: 100%;width: 100%; line-height: 60px;background-color: #545c64;">
    <el-col :span="2">
      <div style="width: 200px; font-weight: bold; color: dodgerblue">后台管理</div>
    </el-col>
    <el-col :span="2" :offset="20">
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-avatar :size="30" :src="user.avatar" style="position: relative; top: 10px"></el-avatar>
          {{ user.chineseName }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="showUserInfo(user.userId)">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/password')">修改密码</el-dropdown-item>
            <el-dropdown-item @click="logout">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
  </el-row>


  <!-- 用户详情  -->
  <!-- <el-dialog v-model="userInfoView" title="个人信息修改" width="30%">
    <el-form :model="userDB">
      <el-form-item label="用户名" label-width="140px">
        <el-input v-model="userDB.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="姓名" label-width="140px">
        <el-input v-model="userDB.chineseName" autocomplete="off" />
      </el-form-item>
      <el-form-item label="手机号" label-width="140px">
        <el-input v-model="userDB.phoneNumber" autocomplete="off" />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="userInfoView = false">取消</el-button>
        <el-button type="primary" @click="update">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog> -->
  <UserInfoView v-model:visible="userInfoView" v-model="userDB" @refreshUser="refreshUser"></UserInfoView>


</template>

<script setup lang="ts">
import router from '@/router';
import UserInfoView from '../user/UserInfoView.vue';
import { userInfo, updateUser } from '@/api/user';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';

interface User {
  userId: number | null;
  username: string;
  password: string;
  chineseName: string;
  avatar: string;
}
const props = defineProps<{ user: User }>()
const emit = defineEmits(['refresh'])

const userInfoView = ref(false)
const userDB = ref({
  userId: null,
  username: '',
  chineseName: '',
  phoneNumber: ''
})
const showUserInfo = (userId: number | null) => {
  userInfo(userId).then(res => {
    if (res.code === 200) {
      userDB.value = res.user
      userInfoView.value = true
    }
  })
}
const update = () => {
  updateUser(userDB.value).then(res => {
    if (res.code === 200) {
      ElMessage.success("更新成功")
      sessionStorage.setItem("user", JSON.stringify(userDB.value));
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const refreshUser  = (value:any)=>{
  emit('refresh',value)
}


const logout = () => {
  sessionStorage.removeItem("user")
  router.push("/login")
}

</script>

<style scoped>
.el-button--text {
  margin-right: 15px;
}

.el-select {
  width: 300px;
}

.el-input {
  width: 300px;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>