<template>
  <div class="loginForm">
    <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">欢迎注册</div>
    <el-card>
      <el-form :model="form">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item prop="chineseName">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.chineseName" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item prop="phoneNumber">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.phoneNumber" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="register">注 册</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="goLogin">返 回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import {reactive} from 'vue'
import router from '@/router';
import {registerRequest} from "@/api/login";
import {ElMessage} from 'element-plus'

// do not use same name with ref
const form = reactive({
  username: '',
  password: '',
  chineseName:'',
  phoneNumber:'',
  roleId: 1
})

const register = () => {
  console.log(form)
  registerRequest(form).then(res => {
    if (res.code === 200) {
      ElMessage.success('成功')
      router.push("/login")
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const goLogin = () => {
  router.push("/login")
}

</script>
<style scoped>
.login {
  width: 100%;
  height: 100%;
  /*如果想做背景图片 可以给标签一个class 直接添加背景图*/
  /*background-image: url("/");*/
}

.myStyle {
  width: 100%;
  height: 100%;
}

.loginForm {
  width: 450px;
  height: 430px;
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  margin: auto; /* 有了这个就自动居中了 */
  background: white;
}
</style>