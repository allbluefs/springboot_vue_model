import request from "@/utils/request";

// 用户菜单
export const userMenu = () => {
    return request.post("/sys/menu/nav");
}

// 菜单树形数据
export const menuTree = () => {
    return request.post("/sys/menu/getTreeMenu")
}

// 菜单详情
export const menuInfo = (menuId: number | null) => {
    return request.post("/sys/menu/info/" + menuId)
}
// 保存菜单
export const saveMenu = (menu: any) => {
    return request.post("/sys/menu/save", JSON.stringify(menu))
}
// 更新菜单
export const updateMenu = (menu: any) => {
    return request.post("/sys/menu/update", JSON.stringify(menu))
}
// 删除菜单
export const deleteMenu = (menuId: number | null) => {
    return request.post("/sys/menu/deleteById/" + menuId)
}