import { api } from '@/service/.service-registry'
import { recipeService } from '@/service/.service-registry'
import { store } from '../store'

export class AccountService {
  async login(request) {
    try {
      const loginResponse = await api.post('accounts/login', request)
      if (loginResponse && loginResponse.status === 'SUCCESS') {
        store.setActiveAccountId(loginResponse.accountId)
        store.setActiveAccountSettings(loginResponse.settings)
        store.setActiveAccountUsername(loginResponse.userName)

        const units = await recipeService.getMeasurementUnits(loginResponse.settings.MEASUREMENT_SYSTEM)
        store.setMeasurementUnits(units)
      }
      return loginResponse
    } catch (e) {
      console.error(e)
      return e
    }
  }

  async logout() {
    store.setActiveAccountId(null)
    //await api.post('accounts/logout')
  }

  async register(request) {
    try {
      return await api.post('accounts', request)
    } catch (e) {
      console.error(e)
      return e
    }
  }

  //AccountResponse
  async getAccount(id) {
    try {
      return await api.get(`accounts/${id}`)
    } catch (e) {
      console.error(e)
      return e
    }
  }

  async updateAccount(id, request) {
    try {
      return await api.put(`accounts/${id}`, request)
    } catch (e) {
      console.error(e)
      return e
    }
  }

  async getAccountSettings(id) {
    try {
      return await api.get(`accounts/${id}/settings`)
    } catch (e) {
      console.error(e)
      return e
    }
  }

  async getAllAccountSettings() {
    try {
      return await api.get(`accounts/settings`)
    } catch (e) {
      console.error(e)
      return e
    }
  }

  async getSecurityQuestions() {
    try {
      return await api.get('security-questions')
    } catch (e) {
      console.error(e)
      return e
    }
  }
}
