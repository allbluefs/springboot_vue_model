import request from "@/utils/request";

type loginForm = {
  username: string;
  password: string;
  role: number;
};
type registerForm = {
  username: string;
  password: string;
};
// 登录
export const loginRequest = (params: loginForm) => {
  return request.post("login", params);
};
