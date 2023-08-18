import axios from "axios";

const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
})

export default {
    state() {
        return {
            accountId: null,
            name: null,
            token: null
        };
    },
    getters: {
        accountId(state) {
            return state.accountId;
        },
        name(state) {
            return state.name;
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
            state.accountId = payload.accountId;
            state.token = payload.token;
            state.name = payload.name;
        }
    },
    actions: {
        async login(context, payload) {
            try {
                const response = await instance.post("/api/v1/login", payload);

                context.commit('setUser', {
                    accountId: response.data.data.accountId,
                    name: response.data.data.name,
                    token: response.data.data.accessToken
                });
                alert(response.data.message);
            } catch (error) {
                throw error.response.data.message
            }
        },
        logout(context) {
            context.commit('setUser', {
                accountId: null,
                name: null,
                token: null
            });
        },
    }
};
