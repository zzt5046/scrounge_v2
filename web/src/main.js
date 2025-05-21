import { createApp } from 'vue'
import router from './router'
import axios from 'axios'
import './assets/css/main.css'
import 'bootstrap/dist/css/bootstrap.css';
import * as bootstrap from 'bootstrap'
import App from '@/App.vue'
import i18n from '@/i18n/i18n'
import VuelidatePlugin from '@vuelidate/core'
import loadStaticData from './static-data';

await loadStaticData().then(() => {
    const app = createApp(App).use(router, axios, bootstrap, VuelidatePlugin)
    app.use(i18n)
    app.mount('#app')
}).catch((error) => {
    console.error('Error loading static data:', error);
});