import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'Layout',
        redirect: "/home",
        component: () => import('@/components/Layout'),
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('@/views/Home')
            },
            {
                path: '/user',
                name: 'User',
                component: () => import('@/views/User')
            },
            {
                path: '/menu',
                name: 'Menu',
                component: () => import('@/views/Menu')
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/views/Register')
    },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})
// 在刷新页面的时候重置当前路由
activeRouter()

function activeRouter() {
    const userStr = sessionStorage.getItem("user")
    console.log("当前登录用户" + userStr)
    if (userStr) {
        const user = JSON.parse(userStr)
        let root = {
            path: '/',
            name: 'Layout',
            component: () => import('@/components/Layout'),
            redirect: "/home",
            children: []
        }
        if (user.permissions) {
            user.permissions.forEach(p => {
                let obj = {
                    path: p.path,
                    name: p.content,
                    component: () => import("@/views/" + p.content)
                };
                root.children.push(obj)
            })
        }
        if (router) {
            router.addRoute(root)
        }
    }
}


// 跳转前判断
router.beforeEach((to, from, next) => {
    if (to.path === '/login' || to.path === '/register') {
        next()
        return
    }
    let user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    if (!user.permissions || !user.permissions.length) {
        next('/login')
    } else if (!user.permissions.find(p => p.path === to.path)) {
        next('/login')
    } else {
        next()
    }
})

export default router
