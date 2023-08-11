<template>
  <div>

    <el-container>
      <el-header style="padding: 0 0">
        <!--    头部-->
        <Header :user="user"/>
      </el-header>
      <el-container>
        <!--        侧栏-->
        <el-aside style="width: 200px;">
          <Aside/>
        </el-aside>
        <el-main>
          <router-view @userInfo="refreshUser"/>
        </el-main>
      </el-container>
    </el-container>


  </div>


</template>

<script setup lang="ts">
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";
import request from "@/utils/request";
import {ref,onMounted} from "vue";

const user = ref({});

const refreshUser = () => {
  let userJson = sessionStorage.getItem("user");
  if (!userJson) {
    return;
  }
  let userId = JSON.parse(userJson).userId;
  // 获取用户信息
  request.get("/sys/user/info/" + userId).then(res => {
    user.value = res.user;
  });
};

onMounted(() => {
  refreshUser();
});

</script>

<style scoped>

</style>