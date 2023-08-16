<template>
    <el-button type="primary" @click="handleClick">点击获取组件值</el-button>
    <MyDialog v-model:visible="visible" v-model="dialogValue" />
    <h1>弹窗结果是: {{ dialogValue }}</h1>

    <el-button type="primary" @click="handleClick2">点击获取组件值2</el-button>
    <MyDialog v-model:visible="visible2" v-model="dialogValue2" />
    <h1>弹窗2结果是: {{ dialogValue2 }}</h1>
</template>

<script setup lang="ts">
import { ref } from "vue";
import MyDialog from "@/views/test/MyDialog.vue";

const visible = ref(false)
const dialogValue = ref({ name: '', email: '' })

const handleClick = () => {
    visible.value = true;
}

const visible2 = ref(false)
const dialogValue2 = ref({ name: '', email: '' })

const handleClick2 = () => {
    visible2.value = true;
}
</script>

