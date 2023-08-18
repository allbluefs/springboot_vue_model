<template>
  <div>

    <el-container>
      <el-header style="padding: 0 0">
        <!--    头部-->
        <Header :user="user" @refresh="refresh" />
      </el-header>
      <el-container>
        <!--        侧栏-->
        <el-aside style="width: 200px;">
          <Aside />
        </el-aside>
        <el-main>
          <router-view @userInfo="refreshUser" />
        </el-main>
      </el-container>
    </el-container>


  </div>
</template>

<script setup lang="ts">
import Aside from "@/components/layout/Aside.vue";
import Header from "@/components/layout/Header.vue"
import { userInfo } from "@/api/user";
import { ref, onMounted } from "vue";

const user = ref({
  userId:null,
  username:'',
  password:'',
  chineseName:'',
  photo:''
});

const refreshUser = () => {
  let userJson = sessionStorage.getItem("user");
  if (!userJson) {
    return;
  }
  let userId = JSON.parse(userJson).userId;
  // 获取用户信息
  userInfo(userId).then(res => {
    user.value = res.user;
  });
};

const refresh = (value:any)=>{
  user.value = value
  sessionStorage.setItem('user',JSON.stringify(value))
}

onMounted(() => {
  refreshUser();
});

</script>

<style scoped></style>