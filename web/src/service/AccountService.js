import { api } from '@/service/.service-registry'

export class AccountService {
  async login(request) {
    try {
      const loginResponse = await api.post('accounts/login', request)

      //if login is successful, set the active account
      // if(loginResponse == 200) {
      //   api.post('accounts/setActive', request)
      // }

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
