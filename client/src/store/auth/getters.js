export default {
    accountId(state) {
        return state.accountId;
    },
    token(state) {
        return state.token;
    },
    isAuthenticated(state) {
        return !!state.token;
    }
};
