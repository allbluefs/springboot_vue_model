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
            <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/password')">修改密码</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
  </el-row>
</template>

<script setup>
import {defineProps} from 'vue';
const props = defineProps(['user']);
console.log("Header中用户信息打印",props.user.username)
</script>

<style scoped>

</style>