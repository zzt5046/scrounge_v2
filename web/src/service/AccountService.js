import { api } from '@/service/.service-registry'
import { recipeService } from '@/service/.service-registry'
import { store } from '../store'

export class AccountService {
  async login(request) {
    const loginResponse = await api.post('accounts/login', request)
    if (loginResponse && loginResponse.status === 'SUCCESS') {
      store.setActiveAccountId(loginResponse.accountId)
      store.setActiveAccountSettings(loginResponse.settings)
      store.setActiveAccountUsername(loginResponse.userName)

      const units = await recipeService.getMeasurementUnits(loginResponse.settings.MEASUREMENT_SYSTEM)
      store.setMeasurementUnits(units)
    }
    return loginResponse
  }

  async logout() {
    store.setActiveAccountId(null)
    //await api.post('accounts/logout')
  }

  async register(request) {
    return await api.post('accounts', request)
  }

  //AccountResponse
  async getAccount(id) {
    return await api.get(`accounts/${id}`)
  }

  async updateAccount(id, request) {
    return await api.put(`accounts/${id}`, request)
  }

  async getAccountSettings(id) {
    return await api.get(`accounts/${id}/settings`)
  }

  async getAllAccountSettings() {
    return await api.get(`accounts/settings`)
  }

  async getSecurityQuestions() {
    return await api.get('security-questions')
  }
}
