import { reactive } from 'vue'

export const store = reactive({

  // Account ID of the user that is currently logged in
  activeAccountId: null,
  setActiveAccountId(accountId) {
    this.activeAccountId = accountId
  },

  // Account of the user that is currently logged in
  activeAccount: null,
  setActiveAccount(account) {
    this.activeAccount = account
  },

  // Account username of the user that is currently logged in
  activeAccountUsername: null,
  setActiveAccountUsername(username) {
    this.activeAccountUsername = username
  },

  // All account settings that are available in the system
  // This is a map where the key is the setting name and the value a list of possible values
  allAccountSettings: null,
  setAllAccountSettings(settings) {
    this.allAccountSettings = settings
  },

  // Account settings of the user that is currently logged in
  // This is a map where the key is the setting name and the value the selected value
  activeAccountSettings: null,
  setActiveAccountSettings(settings) {
    this.activeAccountSettings = settings
  },

  // All security questions that are available in the system
  securityQuestions: [],
  setSecurityQuestions(questions) {
    this.securityQuestions = questions
  },

  // Measurement units that are applicable to the user that is currently logged in
  measurementUnits: {},
  setMeasurementUnits(units) {
    this.measurementUnits = units
  },

  // The name of the currently selected dashboard view
  currentDashboardView: null,
  setCurrentDashboardView(view) {
    this.currentDashboardView = view
  },
})