<template>
  <el-row style="height: 100%;width: 100%; line-height: 60px;background-color: #545c64;">
    <el-col :span="2">
      <div class="title">后台管理</div>
    </el-col>
    <el-col :span="2" :offset="20">
      <el-dropdown>
        <span class="el-dropdown-link">
          <el-avatar :size="30" :src="user.photo" style="position: relative; top: 10px"></el-avatar>
          <span style="color: white;"> {{ user.chineseName }}</span>
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="showUserInfo(user.userId)">个人信息</el-dropdown-item>
            <el-dropdown-item @click="showUpdatePassword">修改密码</el-dropdown-item>
            <el-dropdown-item @click="logout">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
  </el-row>


  <!-- 用户详情  -->
  <UserInfo v-model:visible="userInfoView" v-model="userDB" @refreshUser="refreshUser"></UserInfo>
  <!-- 修改密码 -->
  <PasswordUpdate v-model:visible="updatePasswordView"></PasswordUpdate>
</template>

<script setup lang="ts">
import router from '@/router';
import UserInfo from '../user/UserInfo.vue';
import { userInfo } from '@/api/user';
import { ref } from 'vue';
import PasswordUpdate from '../user/PasswordUpdate.vue';
import { ElMessageBox } from 'element-plus';

interface User {
  userId: number | null;
  username: string;
  password: string;
  chineseName: string;
  photo: string;
}
defineProps<{ user: User }>()
const emit = defineEmits(['refresh'])

// 修改用户信息
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

// 修改密码
const updatePasswordView = ref(false)
const showUpdatePassword = () => {
  updatePasswordView.value = true
}

const refreshUser = (value: any) => {
  emit('refresh', value)
}

const logout = () => {
  ElMessageBox.confirm(
    '是否退出登录?',
    'Warning',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    sessionStorage.removeItem("user")
    router.push("/login")
  })
}

</script>

<style scoped>
.title {
  margin-left: 15%;
  font-family: '华文行楷';
  font-size: 30px;
  font-weight: bold;
  color: white
}

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