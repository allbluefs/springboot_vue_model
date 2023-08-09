<template>
  <div class="loginForm">
    <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">欢迎登录</div>
    <el-card>
      <el-form :model="form">
        <el-form-item prop="username">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.username" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="login">登 录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import {reactive} from 'vue'
import {useRouter} from "vue-router";
import {loginRequest} from "@/api/user";
import {ElMessage} from 'element-plus'

// do not use same name with ref
const form = reactive({
  username: '',
  password: '',
  role: 1
})

const router = useRouter();

const login = () => {
  console.log(form)
  loginRequest(form).then(res => {
    if (res.code === 200) {
      ElMessage({
        message: 'Congrats, this is a success message.',
        type: 'success',
      })
      sessionStorage.setItem('user', JSON.stringify(res.user))
      router.push("/")
    } else {
      ElMessage({
        message: res.msg,
        type: 'error',
      })
    }
  })
}

const register = () => {
  router.push("/register")
}

import {onBeforeMount, onMounted, onBeforeUnmount, onUnmounted} from "vue";

onBeforeMount(() => {
  console.log("组件挂载前");
});

onMounted(() => {
  sessionStorage.removeItem("user")
  console.log("组件挂载完成");
});

onBeforeUnmount(() => {
  console.log("组件卸载之前");
});

onUnmounted(() => {
  console.log("组件卸载完成");
});
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