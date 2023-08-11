import {createRouter, createWebHistory} from 'vue-router'
import userLogin from "@/pages/UserLogin";
import UserSignUp from "@/pages/UserSignUp";


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: [
        {path: '/login', component: userLogin},
        {path: '/signUp', component: UserSignUp}
    ]
})

export default router
