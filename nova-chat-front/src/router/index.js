import { createRouter, createWebHistory } from "vue-router";
import Login from "@/view/Login.vue";
import Register from "@/view/Register.vue"
import ForgetPassword from "@/view/ForgetPassword.vue";
import Chat from "@/view/Chat.vue";

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/forget-password',
        name: 'ForgetPassword',
        component: ForgetPassword
    },
    {
        path: '/',
        name: '/',
        component: Chat,
    },
    {
        path: '/chat',
        name: 'Chat',
        component: Chat
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
});

// 不需要全局前置守卫了
// 登录验证完全依赖后端 Cookie + 响应拦截器
// 当后端返回 401 时，request.js 的响应拦截器会自动跳转到登录页

export default router