import { createApp } from 'vue'
import router from './router'
import axios from 'axios'
import './assets/css/main.css'
import 'bootstrap/dist/css/bootstrap.css'
import bootstrap from 'bootstrap/dist/js/bootstrap'
import App from '@/App.vue'
import i18n from '@/i18n/i18n'

const app = createApp(App).use(router, axios, bootstrap)
app.use(i18n)
app.mount('#app')
