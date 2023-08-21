import request from "@/utils/request";

// 用户菜单
export const userMenu = () => {
    return request.post("/sys/menu/nav");
}

// 菜单分页
export const menuTree = ()=>{
    return request.post("/sys/menu/getTreeMenu")
}