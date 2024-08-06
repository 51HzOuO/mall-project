import {createApp} from 'vue'
import App from './App.vue'
import router from "@/router/index.js";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import store from 'store'
import '@/assets/global.css'

const app = createApp(App);

app.config.globalProperties.$store = store

app.use(router)
    .use(ElementPlus)
    .mount('#app')
