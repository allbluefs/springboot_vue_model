import request from "@/utils/request";

type loginForm = {
    username: string,
    password: string,
    role: number
}
export const loginRequest = (params: loginForm) => {
    return request.post(
        'login', params
    );
}