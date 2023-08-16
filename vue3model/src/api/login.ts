import request from "@/utils/request";

type loginForm = {
  username: string;
  password: string;
};
// 登录
export const loginRequest = (params: loginForm) => {
  return request.post("login", params);
};
type registerForm = {
  username: string;
  password: string;
  chineseName: string;
  phoneNumber: string;
  roleId: number
};
// 注册
export const registerRequest = (params: registerForm) => {
  return request.post("/register", params);
}