<template>
    <div>
        <!--搜索-->
        <div style="float: left">
            <el-form :inline="true" :model="params" class="demo-form-inline">
                <el-form-item>
                    <el-input v-model.trim="params.name" @keyup.enter="query" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="query">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="add">新增</el-button>
                </el-form-item>
            </el-form>
        </div>

        <!--表格数据-->
        <el-table ref="multipleTable" :data="page.list" tooltip-effect="dark" style="width: 100%">
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column prop="roleId" label="角色id">
            </el-table-column>
            <el-table-column prop="roleName" label="角色名称">
            </el-table-column>
            <el-table-column prop="remark" label="备注">
            </el-table-column>
            <el-table-column prop="" label="操作">
                <template #default="scope">
                    <el-button type="primary" size="small" circle @click="update(scope.row.roleId)">
                        <el-icon>
                            <Edit />
                        </el-icon>
                    </el-button>
                    <el-button type="danger" size="small" circle @click="del(scope.row.roleId)">
                        <el-icon>
                            <Delete />
                        </el-icon>
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div style="margin: 20px 30%">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="page.currPage" :page-sizes="[1, 5, 10, 15, 20]" :page-size="page.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="page.totalCount" style="text-align: center">
            </el-pagination>
        </div>

    </div>


    <el-dialog v-model="showInfo" style="width: 30%;">
        <el-form :model="role" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="名称" prop="roleName">
                <el-input v-model="role.roleName" auto-complete="new-password"></el-input>
            </el-form-item>
            <el-form-item label="备注" prop="remark">
                <el-input v-model="role.remark" auto-complete="new-password"></el-input>
            </el-form-item>
            <el-form-item label="菜单" prop="menuIdList">
                <el-tree ref="treeRef" :data="treeData" show-checkbox default-expand-all node-key="menuId" highlight-current
                    :props="defaultProps" />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="saveOrUpdate(ruleForm)">确定</el-button>
                <el-button @click="showInfo = false">返回</el-button>
            </span>
        </template>

    </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElMessageBox, FormRules, FormInstance, ElTree } from 'element-plus';
import { roleInfo, listRole, saveRole, updateRole, deleteRole } from '@/api/role';
import { menuTree } from '@/api/menu';
let params = reactive({
    name: '',
    page: '1',
    limit: '10'
})
const page = ref({
    currPage: 1,
    pageSize: 10,
    totalCount: 0,
    list: []
})
const role = ref()
const showInfo = ref(false)
const ruleForm = ref<FormInstance>()
const rules = reactive<FormRules>({})
const query = () => {
    loadPage()
}
const add = () => {
    role.value = {}
    getMenuTree()
    showInfo.value = true
}
const update = (roleId: number | null) => {
    getMenuTree()
    getRoleInfo(roleId)
}
const del = (roleId: number | null) => {
    ElMessageBox.confirm(
        '是否删除选中的数据?',
        'Warning',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {
        deleteRole(roleId).then(res => {
            if (res.code === 200) {
                ElMessage({
                    type: 'success',
                    message: '删除成功',
                })
                loadPage()
            } else {
                ElMessage({
                    type: 'error',
                    message: res.msg,
                })
            }
        })
    }).catch(() => {
        ElMessage({
            type: 'info',
            message: '取消删除',
        })
    })
}
const handleSizeChange = (val: number) => {
    params.limit = val.toString()
    loadPage()
}
const handleCurrentChange = (val: number) => {
    params.page = val.toString()
    loadPage()
}
const saveOrUpdate = (formEl: FormInstance | undefined) => {
    getCheckedKeys()
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            if (role.value.roleId == null) {
                saveRole(role.value).then(res => {
                    if (res.code === 200) {
                        ElMessage({
                            message: 'success',
                            type: 'success'
                        })
                        loadPage()
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'error'
                        })
                    }
                })
            } else {
                updateRole(role.value).then(res => {
                    if (res.code === 200) {
                        ElMessage({
                            message: 'success',
                            type: 'success'
                        })
                        loadPage()
                    } else {
                        ElMessage({
                            message: res.msg,
                            type: 'error'
                        })
                    }
                })
            }
        }
    })
}
const loadPage = () => {
    listRole(params).then((res: any) => {
        if (res.code === 200) {
            page.value = res.page
        } else {
            ElMessage.error(res.msg)
        }
    })
}
const getRoleInfo = (roleId: number | null) => {
    resetChecked()
    roleInfo(roleId).then((res: any) => {
        if (res.code === 200) {
            role.value = res.role
            setCheckedKeys(role.value.menuIdList)
            showInfo.value = true
        } else {
            ElMessage.error(res.msg)
        }
    })
}
const treeRef = ref<InstanceType<typeof ElTree>>()
const treeData = ref()
const defaultProps = {
    children: 'children',
    label: 'name',
}
const getMenuTree = () => {
    menuTree().then(res => {
        treeData.value = res.data
    })
}
const getCheckedKeys = () => {
    console.log(treeRef.value?.getCheckedKeys(false))
    role.value.menuIdList = treeRef.value?.getCheckedKeys(false)
}
const setCheckedKeys = (keys: Array<number>) => {
    treeRef.value?.setCheckedKeys(keys, false)
}
const resetChecked = () => {
    treeRef.value?.setCheckedKeys([], false)
}
onMounted(() => {
    loadPage()
})
</script>

<style scoped>
.el-input {
    width: 350px;
}
</style>