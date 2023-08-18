<template>
    <el-dialog v-model="visible" title="个人密码修改" width="30%">
        <el-form :model="passwordForm" ref="passwordFormRef" :rules="rules" label-position="left">
            <el-form-item label="原密码" label-width="100px" prop="oldPassword">
                <el-input v-model="passwordForm.oldPassword" type="password" show-password autocomplete="off" />
            </el-form-item>
            <el-form-item label="新密码" label-width="100px" prop="newPassword">
                <el-input v-model="passwordForm.newPassword" type="password" show-password autocomplete="off" />
            </el-form-item>
            <el-form-item label="确认密码" label-width="100px" prop="confirmPassword">
                <el-input v-model="passwordForm.confirmPassword" type="password" show-password autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="emit('update:visible', false)">取消</el-button>
                <el-button type="primary" @click="update(passwordFormRef)">
                    确认
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup lang="ts">
import { computed, ref, reactive } from 'vue';
import { ElMessage, FormInstance, FormRules } from 'element-plus';
import { updatePassword } from '@/api/user';

const props = defineProps(['visible'])
const emit = defineEmits(['update:visible'])

const visible = computed({
    get() {
        return props.visible
    },
    set(value) {
        emit('update:visible', value)
    }
})
interface PasswordForm {
    oldPassword: string
    newPassword: string
    confirmPassword: string
}
const passwordFormRef = ref<FormInstance>()
const comparePassword = (rule: any, value: string, callback: any) => {
    if (passwordForm.value.newPassword === '') {
        callback(new Error('请输入新密码'))
    } else {
        if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
            callback(new Error('两次密码不一致'))
        } else {
            callback()
        }
    }
}
const rules = reactive<FormRules<PasswordForm>>({
    oldPassword: [
        { required: true, message: '原密码不能为空', trigger: 'blur' },
        { min: 3, max: 10, message: '密码为3-10个字符', trigger: 'blur' },
    ],
    newPassword: [
        { required: true, message: '新密码不能为空', trigger: 'blur' },
        { min: 3, max: 10, message: '密码为3-10个字符', trigger: 'blur' },
    ],
    confirmPassword: [
        { required: true, message: '新密码不能为空', trigger: 'blur' },
        { validator: comparePassword, trigger: 'blur' }
    ]
})
const passwordForm = ref<PasswordForm>({
    oldPassword: '',
    newPassword: '',
    confirmPassword: ''
})
const update = async (formEl: FormInstance | undefined) => {
    if (!formEl) {
        return
    }
    await formEl.validate((valid, fields) => {
        if (valid) {
            updatePassword(passwordForm.value).then(res => {
                if (res.code === 200) {
                    ElMessage.success('更新成功')
                    passwordForm.value = {
                        oldPassword: '',
                        newPassword: '',
                        confirmPassword: ''
                    }
                    emit('update:visible', false)
                } else {
                    ElMessage.error(res.msg)
                }
            })
        } else {
            console.log('error submit!', fields)
        }
    })
}
</script>