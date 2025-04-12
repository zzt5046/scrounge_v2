import { reactive } from 'vue'

export const store = reactive({
    
  activeAccountId: null,
  setActiveAccountId(accountId) {
    this.activeAccountId = accountId
  },

  activeAccountSettings: null,
  setActiveAccountSettings(settings) {
    this.activeAccountSettings = settings
  },

  currentDashboardView: null,
  setCurrentDashboardView(view) {
    this.currentDashboardView = view
  },
})