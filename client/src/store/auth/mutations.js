export default {
    setUser(state, payload) {
        state.token = payload.token;
        state.accountId = payload.accountId;
    }
};
