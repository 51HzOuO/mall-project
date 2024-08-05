import {createRouter, createWebHistory} from "vue-router";
import Register from "@/views/user/Register.vue";
import index from "@/views/index.vue";
import FurnManage from "@/views/user/manager/FurnManage.vue";

const routes = [
    {path: '/', component: index},
    {path: '/register', component: Register},
    {path: '/user/furn_manage', component: FurnManage, meta: {title: '后台管理'}}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    document.title = to.meta.title || 'Title'
    next()
})

export default router
