import { reactive } from 'vue'

export const store = reactive({

  // User data
  activeAccountId: null,
  setActiveAccountId(accountId) {
    this.activeAccountId = accountId
  },

  activeAccountUsername: null,
  setActiveAccountUsername(username) {
    this.activeAccountUsername = username
  },

  activeAccountSettings: null,
  setActiveAccountSettings(settings) {
    this.activeAccountSettings = settings
  },

  securityQuestions: [],
  setSecurityQuestions(questions) {
    this.securityQuestions = questions
  },

  measurementUnits: {},
  setMeasurementUnits(units) {
    this.measurementUnits = units
  },

  // Dashboard data
  currentDashboardView: null,
  setCurrentDashboardView(view) {
    this.currentDashboardView = view
  },
})