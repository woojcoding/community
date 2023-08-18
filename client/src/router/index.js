import {createRouter, createWebHistory} from 'vue-router'
import userLogin from "@/pages/UserLogin";
import UserSignUp from "@/pages/UserSignUp";
import CommunityHome from "@/pages/CommunityHome";
import store from "@/store/index"
import FreeBoardListView from "@/pages/board/free/FreeBoardListView";
import FreeBoardDetail from "@/pages/board/free/FreeBoardDetail";
import FreeBoardModifyView from "@/pages/board/free/FreeBoardModifyView";
import FreeBoardPostView from "@/pages/board/free/FreeBoardPostView";

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes: [
        {path: '/login', component: userLogin},
        {path: '/signUp', component: UserSignUp},
        {
            path: '/boards/free',
            component: FreeBoardListView,
            props: {type: "free"}
        },
        {
            path: '/boards/free/:boardId',
            component: FreeBoardDetail,
            props: (route) => ({
                boardId: route.params.boardId,
                query: route.query
            })
        },
        {
            path: '/boards/free/post',
            component: FreeBoardPostView,
            props: (route) => ({
                query: route.query
            })
        },
        {
            path: '/boards/free/modify/:boardId',
            component: FreeBoardModifyView,
            props: (route) => ({
                boardId: route.params.boardId,
                query: route.query
            })
        },
        {path: '/boards/notice', component: FreeBoardListView},
        {path: '/boards/gallery', component: FreeBoardListView},
        {path: '/boards/help', component: FreeBoardListView},
        {path: '/home', component: CommunityHome},
    ]
})

router.beforeEach((to, _, next) => {
    const isAuthenticated = store.getters.isAuthenticated;

    // 로그인 페이지에 접근하려고 하면, 로그인 후에 다른 주소로 리다이렉트
    if (to.path === '/login' && isAuthenticated) {
        next('/home');
    } else {
        next(); // 다른 페이지로 이동을 허용
    }
});

export default router
