import { accountService } from '@/service/.service-registry'
import { store } from './store';

// Load all static data that is needed for the application
export default async function loadStaticData() {
    await loadAllAccountSettings()
    await loadSecurityQuestions()
}

// Fetch security questions
async function loadSecurityQuestions() {
    let questions = []
    await accountService.getSecurityQuestions().then((response) => {
        response.forEach((question) => {
            questions.push({
                id: question.id,
                name: question.question
            })
        })
    })
    store.setSecurityQuestions(questions)
}

// Fetch all possible account settings 
async function loadAllAccountSettings() {
    await accountService.getAllAccountSettings().then((response) => {
        store.setAllAccountSettings(response)
    })
}