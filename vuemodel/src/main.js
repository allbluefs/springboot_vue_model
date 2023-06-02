import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
import VueParticles from 'vue-particles'
import locale from "element-plus/lib/locale/lang/zh-cn"
// 处理页面四周空白
import '@/assets/css/global.css'

const app = createApp(App)
    .use(store)
    .use(router)
    .use(ElementPlus, {size: 'default', locale})
    .use(VueParticles)
    .mount('#app')
