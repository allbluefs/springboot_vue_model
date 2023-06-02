<template>
  <div>
    <!--    头部-->
    <Header :user="user"/>
    <div style="display: flex;">
      <Aside/>
      <router-view style="flex: 1" @userInfo="refreshUser"/>
    </div>
  </div>


</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";
import request from "@/utils/request";

export default {
  name: "Layout",
  components: {Aside, Header},
  data() {
    return {
      user: {}
    }
  },
  methods: {
    refreshUser() {
      let userJson = sessionStorage.getItem("user")
      if (!userJson) {
        return
      }
      let userId = JSON.parse(userJson).userId
      // 获取用户信息
      request.get("/sys/user/info/" + userId).then(res => {
        this.user = res
      })
    }
  },
  created() {
    this.refreshUser()
  },
}
</script>

<style scoped>

</style>