import request from "@/utils/request";

// 角色详情
export const roleInfo = (roleId: number | null) => {
    return request.post("/sys/role/info/" + roleId)
}
// 角色分页
export const listRole = (params: any) => {
    return request.post("/sys/role/list", JSON.stringify(params))
}
// 保存角色
export const saveRole = (role: any) => {
    return request.post("/sys/role/save", JSON.stringify(role))
}
// 更新角色
export const updateRole = (role: any) => {
    return request.post("/sys/role/update", JSON.stringify(role))
}
// 删除角色
export const deleteRole = (roleId: number | null) => {
    return request.post("/sys/role/deleteById/" + roleId)
}