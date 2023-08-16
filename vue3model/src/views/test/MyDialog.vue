<template>
    <el-dialog v-model="visible" title="用户录入">
        <el-form>
            <el-form-item label="姓名">
                <el-input v-model="name" autocomplete="off" />
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="email" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="visible = false">取消</el-button>
                <el-button type="primary" @click="handleClick">确认</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';

const props = defineProps(['visible', 'modelValue'])
const emit = defineEmits(['update:visible', 'update:modelValue'])


const visible = computed({
    get() {
        return props.visible
    },
    set(value) {
        emit('update:visible', value)
    }
})

const name = ref('')
const email = ref('')

const handleClick = () => {
    emit('update:visible', false)
    emit('update:modelValue', { name, email })
}
</script>

