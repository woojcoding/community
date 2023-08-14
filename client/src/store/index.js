import {createStore} from 'vuex';

import authModule from './auth/index.js';
import createPersistedState from "vuex-persistedstate";

const store = createStore({
    modules: {
        auth: authModule
    },
    plugins: [
        createPersistedState({
            paths: ["auth"]
        })
    ]
});

export default store;
