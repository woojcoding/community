import {createRouter, createWebHistory} from 'vue-router'
import userLogin from "@/pages/UserLogin";
import UserSignUp from "@/pages/UserSignUp";
import BoardList from "@/pages/BoardList";
import CommunityHome from "@/pages/CommunityHome";
import store from "@/store/index"

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: [
        {path: '/login', component: userLogin},
        {path: '/signUp', component: UserSignUp},
        {path: '/boards', component: BoardList},
        {path: '/home', component: CommunityHome},
    ]
})

router.beforeEach((to,_, next) => {
    const isAuthenticated = store.getters.isAuthenticated;

    // 로그인 페이지에 접근하려고 하면, 로그인 후에 다른 주소로 리다이렉트
    if (to.path === '/login' && isAuthenticated) {
        next('/home');
    } else {
        next(); // 다른 페이지로 이동을 허용
    }
});

export default router
