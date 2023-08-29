import {createApp} from 'vue'
import App from './App.vue'
import router from "@/router";
import store from "@/store"
import '@fortawesome/fontawesome-free/css/all.css';
import 'bootstrap/dist/css/bootstrap.min.css' // 부트스트랩 CSS
import 'bootstrap/dist/js/bootstrap.min.js' // 부트스트랩 JavaScript

const app = createApp(App)

app.use(router);
app.use(store);

app.mount('#app')
