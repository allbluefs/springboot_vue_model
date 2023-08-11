<template>
  <el-menu
      style="height: calc(100vh - 60px)"
      class="el-menu-vertical-demo"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      router
  >
    <template v-for="item in menuList" :key="item.menuId">
      <sub-menu :item="item"/>
    </template>
  </el-menu>
</template>

<script>
import {User, Edit, Search, Share, Upload, Menu} from '@element-plus/icons-vue'
import SubMenu from "@/components/SubMenu";
import request from "@/utils/request";

export default {
  name: "Aside",
  components: {SubMenu, Edit, User, Share, Upload, Search, Menu},
  data() {
    return {
      menuList: []
    }
  },
  methods: {
    getUserMenu() {
      request.post('/sys/menu/nav').then(res => {
        this.menuList = res.menuList
      }).catch(err => {
        this.$message.error(err)
      })
    }
  },
  async mounted() {
    await this.getUserMenu()
  },

}
</script>

<style scoped>

</style>