import axios from "axios";

const instance = axios.create({
    baseURL: process.env.VUE_APP_API_URL,
})

export default {
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
    tryLogin(context) {
        const token = localStorage.getItem('token');
        const accountId = localStorage.getItem('accountId');

        if (token && accountId) {
            context.commit('setUser', {
                token: token,
                accountId: accountId
            });
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
