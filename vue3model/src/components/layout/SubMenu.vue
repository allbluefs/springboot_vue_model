<template>
  <el-sub-menu :index="item.path" v-if="item.type === 0">
    <template #title>
      <el-icon>
        <component :is="item.icon"></component>
      </el-icon>
      <span>{{ item.name }}</span>
    </template>
    <template v-for="child in item.children" :key="child.menuId">
      <el-menu-item :index="child.path" v-if="child.type === 1">
        <el-icon>
          <component :is="child.icon"></component>
        </el-icon>
        <span>{{ child.name }}</span>
      </el-menu-item>
    </template>
    <template v-for="menuItem in item.children" :key="menuItem.menuId">
      <sub-menu v-if="menuItem.type === 0" :item="menuItem"></sub-menu>
    </template>
  </el-sub-menu>
  <el-menu-item :index="item.path" v-else>
    <el-icon>
      <component :is="item.icon"></component>
    </el-icon>
    <span>{{ item.name }}</span>
  </el-menu-item>
</template>

<script setup lang="ts">
interface MenuItem {
  menuId: number;
  path: string;
  type: number;
  name: string;
  icon: string,
  children?: MenuItem[];
}
defineProps<{ item: MenuItem }>();
</script>

<style scoped></style>