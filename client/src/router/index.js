import {createRouter, createWebHistory} from 'vue-router'
import userLogin from "@/pages/UserLogin";
import UserSignUp from "@/pages/UserSignUp";
import CommunityHome from "@/pages/CommunityHome";
import store from "@/store/index"
import FreeBoardListView from "@/pages/board/free/FreeBoardListView";
import FreeBoardDetail from "@/pages/board/free/FreeBoardDetail";
import FreeBoardModifyView from "@/pages/board/free/FreeBoardModifyView";
import FreeBoardPostView from "@/pages/board/free/FreeBoardPostView";
import NoticeBoardListView from "@/pages/board/notice/NoticeBoardListView";
import NoticeBoardDetail from "@/pages/board/notice/NoticeBoardDetail";
import HelpBoardListView from "@/pages/board/help/HelpBoardListView";
import HelpBoardDetail from "@/pages/board/help/HelpBoardDetail";
import HelpBoardPostView from "@/pages/board/help/HelpBoardPostView";
import HelpBoardModifyView from "@/pages/board/help/HelpBoardModifyView";
import GalleryBoardDetail from "@/pages/board/gallery/GalleryBoardDetail";
import GalleryBoardListView from "@/pages/board/gallery/GalleryBoardListView";
import GalleryBoardPostView from "@/pages/board/gallery/GalleryBoardPostView";
import GalleryBoardModifyView
    from "@/pages/board/gallery/GalleryBoardModifyView";

const requireAuth = (to, from, next) => {
    if (store.getters.isAuthenticated) {
        next();
    } else {
        next('/login');
    }
};

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
            }),
            beforeEnter: requireAuth
        },
        {
            path: '/boards/free/modify/:boardId',
            component: FreeBoardModifyView,
            props: (route) => ({
                boardId: route.params.boardId,
                query: route.query
            }),
            beforeEnter: requireAuth
        },
        {
            path: '/boards/notice',
            component: NoticeBoardListView,
            props: {type: "notice"}
        },
        {
            path: '/boards/notice/:boardId',
            component: NoticeBoardDetail,
            props: (route) => ({
                boardId: route.params.boardId,
                query: route.query
            })
        },
        {
            path: '/boards/gallery',
            component: GalleryBoardListView,
            props: {type: "gallery"}
        },
        {
            path: '/boards/gallery/:boardId',
            component: GalleryBoardDetail,
            props: (route) => ({
                boardId: route.params.boardId,
                query: route.query
            })
        },
        {
            path: '/boards/gallery/post',
            component: GalleryBoardPostView,
            props: (route) => ({
                query: route.query
            }),
            beforeEnter: requireAuth
        },
        {
            path: '/boards/gallery/modify/:boardId',
            component: GalleryBoardModifyView,
            props: (route) => ({
                boardId: route.params.boardId,
                query: route.query
            }),
            beforeEnter: requireAuth
        },
        {
            path: '/boards/help',
            component: HelpBoardListView,
            props: {type: "help"}
        },
        {
            path: '/boards/help/:boardId',
            component: HelpBoardDetail,
            props: (route) => ({
                boardId: route.params.boardId,
                query: route.query
            })
        },
        {
            path: '/boards/help/post',
            component: HelpBoardPostView,
            props: (route) => ({
                query: route.query
            }),
            beforeEnter: requireAuth
        },
        {
            path: '/boards/help/modify/:boardId',
            component: HelpBoardModifyView,
            props: (route) => ({
                boardId: route.params.boardId,
                query: route.query
            }),
            beforeEnter: requireAuth
        },
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
