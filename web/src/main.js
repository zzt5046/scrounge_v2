import { createApp } from 'vue'
import router from './router'
import axios from 'axios'
import './assets/css/main.css'
import 'bootstrap/dist/css/bootstrap.css';
import * as bootstrap from 'bootstrap'
import App from '@/App.vue'
import i18n from '@/i18n/i18n'
import { accountService } from '@/service/.service-registry'
import { store } from './store';

await loadStaticData()

const app = createApp(App).use(router, axios, bootstrap)
app.use(i18n)
app.mount('#app')

async function loadStaticData() {
    let questions = []
    accountService.getSecurityQuestions().then((response) => {
        response.forEach((question) => {
            questions.push({
                id: question.id,
                name: question.question
            })
        })
    })

    store.setSecurityQuestions(questions)
}