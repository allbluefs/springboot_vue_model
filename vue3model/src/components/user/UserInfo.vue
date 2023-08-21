<template>
    <el-dialog v-model="visible" title="个人信息修改" width="30%">
        <el-form :model="user" label-position="left">
            <el-form-item label="头像" label-width="100px">
                <el-upload class="avatar-uploader" action="http://127.0.0.1:8080/api/uploadFile" :show-file-list="false"
                    :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="用户名" label-width="100px">
                <el-input v-model="user.username" autocomplete="off" />
            </el-form-item>
            <el-form-item label="姓名" label-width="100px">
                <el-input v-model="user.chineseName" autocomplete="off" />
            </el-form-item>
            <el-form-item label="手机号" label-width="100px">
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

<script setup lang="ts">
import { computed, ref,onMounted } from 'vue';
import { updateUser } from '@/api/user';
import { ElMessage, UploadProps } from 'element-plus';

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
    },
    set() {
        console.log(1)
    }
})
onMounted(()=>{
    imageUrl.value = props.modelValue.photo
})

const update = () => {
    const userParam = {
        userId: user.value.userId,
        username: user.value.username,
        chineseName: user.value.chineseName,
        phoneNumber: user.value.phoneNumber,
        roleId: user.value.roleId,
        photo: user.value.photo
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

const imageUrl = ref('')

const handleAvatarSuccess: UploadProps['onSuccess'] = (
    response,
    uploadFile
) => {
    console.log(uploadFile)
    imageUrl.value = response.data.src
    user.value.photo = response.data.src
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
    if (rawFile.size / 1024 / 1024 > 2) {
        ElMessage.error('Avatar picture size can not exceed 2MB!')
        return false
    }
    return true
}

</script>


<style>
.el-button--text {
    margin-right: 15px;
}

.el-select {
    width: 350px;
}

.el-input {
    width: 350px;
}

.dialog-footer button:first-child {
    margin-right: 10px;
}

.avatar-uploader .avatar {
    width: 50px;
    height: 50px;
    display: block;
}

.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 50px;
    height: 50px;
    text-align: center;
}
</style>
