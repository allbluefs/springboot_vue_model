<template>
  <el-menu style="height: calc(100vh - 60px)" class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff"
    active-text-color="#ffd04b" router>
    <template v-for="item in menuList" :key="item.menuId">
      <sub-menu :item="item" />
    </template>
  </el-menu>
</template>

<script setup>
import SubMenu from "@/components/layout/SubMenu";
import { userMenu } from "@/api/menu";
import { ElMessage } from "element-plus";
import { onMounted, ref } from "vue";
const menuList = ref({})
const getUserMenu = () => {
  userMenu().then(res => {
    menuList.value = res.menuList
  }).catch(err => {
    ElMessage.error(err)
  })
}
onMounted(() => {
  getUserMenu()
})
</script>

<style scoped></style>