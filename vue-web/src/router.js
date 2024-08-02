import {createRouter, createWebHistory} from "vue-router";
import Register from "@/pages/user/Register.vue";
import index from "@/pages/index.vue";

const routes = [
    {path: '/', component: index},
    {path: '/register', component: Register},
]

export const router = createRouter({
    history: createWebHistory(),
    routes
})
