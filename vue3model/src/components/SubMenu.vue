<template>
  <el-sub-menu :index="item.path" v-if="item.type===0">
    <template #title>{{ item.name }}</template>
    <template v-for="child in item.children">
      <el-menu-item :index="child.path" v-if="child.type===1">
        {{ child.name }}
      </el-menu-item>
    </template>
    <template v-for="menuItem in item.children">
      <sub-menu v-if="menuItem.type ===0 " :item="menuItem"></sub-menu>
    </template>
  </el-sub-menu>
  <el-menu-item :index="item.path" v-else>
    {{ item.name }}
  </el-menu-item>
</template>

<script>
export default {
  name: "SubMenu",
  props: {
    item: {}
  },
  created() {
    console.log(this.item)
  },
  mounted() {
    console.log(this.item)
  },
}
</script>

<style scoped>

</style>