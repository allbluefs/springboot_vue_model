import request from "@/utils/request";

// 查询详情
export const userInfo = (userId: number | null) => {
  return request.get("/sys/user/info/" + userId);
};

type pageParams = {
  username: string;
  page: string;
  limit: string;
};
// 用户分页列表
export const listUser = (params: pageParams) => {
  return request.post("/sys/user/list", params);
};

type User = {
  userId: number | null;
  username: string;
  chineseName: string;
  phoneNumber: string;
};

// 保存用户
export const saveUser = (user: User) => {
  return request.post("sys/user/save", user);
};
// 更新用户
export const updateUser = (user: User) => {
  return request.post("sys/user/update", user);
};
// 删除用户
export const deleteUser = (userId: number) => {
  return request.post("/sys/user/deleteById/" + userId);
};
// 修改密码
type PasswordForm = {
  oldPassword: string;
  newPassword: string;
}
export const updatePassword = (passwordForm: PasswordForm) => {
  return request.post('/sys/user/updatePassword', JSON.stringify(passwordForm));
}