import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'Layout',
        redirect: "/home",
        component: () => import('../components/Layout.vue'),
        children: [
            {
                path: '/home',
                name: 'home',
                component: () => import('../views/Home.vue')
            },
            {
                path: '/user',
                name: 'User',
                component: () => import('../views/User.vue')
            },
            {
                path: '/menu',
                name: 'Menu',
                component: () => import('../views/Menu.vue')
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/LoginView.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/RegisterView.vue')
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

// 在刷新页面的时候重置当前路由
activeRouter()

function activeRouter() {
    const userStr = sessionStorage.getItem("user")
    if (userStr) {
        const user = JSON.parse(userStr)
        console.log(user)
        interface Child {
            path: any;
            name: any;
            component: () => Promise<any>; // 这里的 Promise<any> 可能需要根据你的实际情况进行调整
        }

        const root: { path: string, name: string, component: any, redirect: string, children: Child[] } = {
            path: '/',
            name: 'Layout',
            component: () => import('../components/Layout.vue'),
            redirect: "/home",
            children: []
        };
        if (user.permissions) {
            user.permissions.forEach((p: any) => {
                const obj: Child = {
                    path: p.path,
                    name: p.content,
                    component: () => import("../views/" + p.content)
                };
                root.children.push(obj)
            })
        }
        console.log("root为:",root)
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
    console.log("topath:",to.path)
    const userString = sessionStorage.getItem("user");
    console.log("跳转前判断",userString)
    const user = userString ? JSON.parse(userString) : {};
    if (!user.permissions || !user.permissions.length) {
        next('/login')
    } else if (!user.permissions.find((p: any) => p.path === to.path)) {
        next('/login')
    } else {
        next()
    }
})

export default router
