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
    try {
        await accountService.getSecurityQuestions().then((response) => {
            if (response) {
                response.forEach((question) => {
                    questions.push({
                        id: question.id,
                        name: question.question
                    })
                })
            }
        })
        store.setSecurityQuestions(questions)
    } catch (error) {
        console.error('Error loading security questions:', error);
    }
}

// Fetch all possible account settings 
async function loadAllAccountSettings() {
    try {
        await accountService.getAllAccountSettings().then((response) => {
            if (response) {
                store.setAllAccountSettings(response)
            } 
        })
    } catch (error) {
        console.error('Error loading account settings:', error);
    }
}