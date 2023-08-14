import axios from "axios";

const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
})

export default {
    state() {
        return {
            accountId: null,
            token: null
        };
    },
    getters: {
        accountId(state) {
            return state.accountId;
        },
        token(state) {
            return state.token;
        },
        isAuthenticated(state) {
            return !!state.token;
        }
    },
    mutations: {
        setUser(state, payload) {
            state.token = payload.token;
            state.accountId = payload.accountId;
        }
    },
    actions: {
        async login(context, payload) {
            try {
                const response = await instance.post("/api/v1/login", payload);

                localStorage.setItem('token', response.data.data.accessToken);
                localStorage.setItem('accountId', response.data.data.accountId);

                context.commit('setUser', {
                    token: response.data.data.accessToken,
                    accountId: response.data.data.accountId
                });

                alert(response.data.message);
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    alert(error.response.data.message);
                } else {
                    alert('잠시 후 다시 시도해주세요.');
                }
            }
        },
        logout(context) {
            localStorage.removeItem('token');
            localStorage.removeItem('userId');
            localStorage.removeItem('tokenExpiration');

            context.commit('setUser', {
                token: null,
                userId: null
            });
        },
    }
};
