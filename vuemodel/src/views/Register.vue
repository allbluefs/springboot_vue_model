<template>
  <div class="register">
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
      <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">用户注册</div>
      <el-card>
        <el-form ref="form" :model="user" size="normal" :rules="rules">
          <el-form-item label="用户名" prop="username">
            <el-input v-model.trim="user.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model.trim="user.password" show-password></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="chineseName">
            <el-input v-model.trim="user.chineseName"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%" type="primary" @click="register">注 册</el-button>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%" type="primary" @click="cancel">返 回</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "Register",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, message: '密码最少3位', trigger: 'blur'}
        ],
        chineseName: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    register() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post('/register', this.user).then(res => {
            if (res.code === 200) {
              this.$message.success("注册成功")
            }
            this.$router.push('/')
          })
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    cancel() {
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
.register {
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