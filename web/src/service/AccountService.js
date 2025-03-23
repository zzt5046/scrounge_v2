import { api } from '@/service/.service-registry'

export class AccountService {
  async login(request) {
    try {
      const loginResponse = await api.post('accounts/login', request)
      console.log(loginResponse)
      return loginResponse
    } catch (e) {
      console.error(e)
    }
  }

  async register(request) {
    try {
      return await api.post('accounts', request)
    } catch (e) {
      console.error(e)
    }
  }

  async getSecurityQuestions() {
    try {
      return await api.get('security-questions')
    } catch (e) {
      console.error(e)
    }
  }
}
