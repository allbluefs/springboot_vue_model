<template>
  <div class="login">
    <vue-particles
        color="#39AFFD"
        class="myStyle"
        :particleOpacity="1"
        :particlesNumber="60"
        shapeType="circle"
        :particleSize="20"
        linesColor="#8DD1FE"
        :linesWidth="1"
        :lineLinked="false"
        :lineOpacity="0.4"
        :linesDistance="150"
        :moveSpeed="3"
        :hoverEffect="true"
        hoverMode="bubble"
        :clickEffect="false"
        clickMode="push">
    </vue-particles>
    <div class="loginForm">
      <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">欢迎登录</div>
      <el-card>
        <el-form ref="form" :model="form" size="normal" :rules="rules">
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
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "Login",
  components: {},
  data() {
    return {
      form: {role: 1},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      },
    }
  },
  mounted() {
    sessionStorage.removeItem("user")
  },
  methods: {
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/login", this.form).then(res => {
            if (res.code === 200) {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.user))  // 缓存用户信息
              // 登录成功的时候更新当前路由
              this.$router.push("/")  //登录成功之后进行页面的跳转，跳转到主页
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    },
    register() {
      this.$router.push("/register")
    }
  }
}
</script>

<style scoped>
.login{
  width: 100%;
  height: 100%;
  /*如果想做背景图片 可以给标签一个class 直接添加背景图*/
  /*background-image: url("/");*/
}
.myStyle{
  width: 100%;
  height: 100%;
}
.loginForm{
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
