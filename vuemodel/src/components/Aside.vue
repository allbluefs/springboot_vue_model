<template>
  <el-menu
      style="width: 200px; height: calc(100vh - 50px)"
      class="el-menu-vertical-demo"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      router
  >
    <!--管理员-->
    <el-menu-item index="/user">
      <el-icon>
        <User/>
      </el-icon>
      <span slot="title">用户管理</span>
    </el-menu-item>
    <el-menu-item index="/menu">
      <el-icon>
        <Menu/>
      </el-icon>
      <span slot="title">菜单管理</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
import { User, Edit, Search, Share, Upload,Menu } from '@element-plus/icons-vue'
export default {
  name: "Aside",
  components:{Edit,User,Share,Upload,Search,Menu}
}
</script>

<style scoped>

</style>