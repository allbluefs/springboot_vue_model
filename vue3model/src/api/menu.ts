import request from "@/utils/request";

export const userMenu = () => {
    return request.post("/sys/menu/nav");
}