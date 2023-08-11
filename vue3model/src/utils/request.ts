import axios, {AxiosRequestConfig} from "axios";
import router from "@/router";
import {ElMessage} from 'element-plus'

const request = axios.create({
    baseURL: "/api",
    timeout: 5000
})

// 请求白名单，如果请求在白名单里面，将不会被拦截校验权限
const whiteUrls = ["/login", '/register']
// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    // 取出sessionStorage里面缓存的用户信息
    const userJson = sessionStorage.getItem("user")
    if (config.url && !whiteUrls.includes(config.url)) {  // 校验请求白名单
        if (!userJson) {
            router.push("/login")
        } else {
            const user = JSON.parse(userJson);
            config.headers['token'] = user.token;  // 设置请求头
        }
    }
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        // 验证token
        if (res.code === 401) {
            console.error("token过期，重新登录")
            ElMessage({
                type: 'error',
                message: res.msg,
            })
            router.push("/login")
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)

// 声明返回的参数
declare module "axios" {
    interface AxiosResponse<T = any> {
        code: number;
        msg: string;
        // 这里追加你的参数
        list:any;
        user:any;
    }
    export function create(config?: AxiosRequestConfig): AxiosInstance;
}

export default request

