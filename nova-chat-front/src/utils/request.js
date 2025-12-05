import axios from 'axios'
import router from "@/router/index.js";

// 创建axios实例
const request = axios.create({
    baseURL: import.meta.env.VITE_BASE_API, // 基础URL设置
    timeout: 10000, // 请求超时时间
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true, // 允许携带Cookie
})

// 请求拦截器
request.interceptors.request.use(
    config => config,
    error => Promise.reject(error)
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        // 对响应数据做点什么
        return response.data
    },
    error => {
        // 对响应错误做处理
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    // 未授权，跳转到登录页，并保存当前路径
                    // 避免在登录页重复跳转
                    if (router.currentRoute.value.path !== '/login') {
                        router.push({
                            path: '/login',
                            query: { redirect: router.currentRoute.value.fullPath }
                        })
                    }
                    break
                case 403:
                    // 权限不足
                    console.error('权限不足')
                    break
                default:
                    console.error('请求错误', error.response.status)
            }
        }
        return Promise.reject(error)
    }
)

export default request