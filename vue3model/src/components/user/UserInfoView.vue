<template>
    <el-dialog v-model="visible" title="个人信息修改" width="30%">
        <el-form :model="user">
            <el-form-item label="用户名" label-width="140px">
                <el-input v-model="user.username" autocomplete="off" />
            </el-form-item>
            <el-form-item label="姓名" label-width="140px">
                <el-input v-model="user.chineseName" autocomplete="off" />
            </el-form-item>
            <el-form-item label="手机号" label-width="140px">
                <el-input v-model="user.phoneNumber" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="emit('update:visible', false)">取消</el-button>
                <el-button type="primary" @click="update">
                    确认
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { ref, computed } from 'vue';
import { updateUser } from '@/api/user';
import { ElMessage } from 'element-plus';

const props = defineProps(['visible', 'modelValue'])
const emit = defineEmits(['update:visible', 'update:modelValue', 'refreshUser'])

const visible = computed({
    get() {
        return props.visible
    },
    set(value) {
        emit('update:visible', value)
    }
})
const user = computed({
    get() {
        return props.modelValue
    }
})

const update = () => {
    const userParam = {
        userId: user.value.userId,
        username: user.value.username,
        chineseName: user.value.chineseName,
        phoneNumber: user.value.phoneNumber,
        roleId: user.value.roleId
    }
    updateUser(userParam).then((res) => {
        if (res.code === 200) {
            ElMessage.success("保存成功")
            emit('update:visible', false)
            res.user.token = user.value.token
            res.user.permissions = user.value.permissions
            emit('refreshUser', res.user)
        }
    })
}

</script>

<style scoped></style>
