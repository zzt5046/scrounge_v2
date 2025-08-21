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

  // All recipes that belong to the active account. Array of RecipeResponse
  activeAccountRecipes: null,
  setActiveAccountRecipes(recipes) {
    this.activeAccountRecipes = recipes
  },

  // Inventory that belongs to the active account. Array of Strings
  activeAccountInventory: null,
  setActiveAccountInventory(inventory) {
    this.activeAccountInventory = inventory
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

  recipePreferences: [
    { "id": "easy", "name": "Easy" },
    { "id": "intermediate", "name": "Intermediate" },
    { "id": "advanced", "name": "Advanced" },
    { "id": "italian", "name": "Italian" },
    { "id": "french", "name": "French" },
    { "id": "mexican", "name": "Mexican" },
    { "id": "japanese", "name": "Japanese" },
    { "id": "chinese", "name": "Chinese" },
    { "id": "korean", "name": "Korean" },
    { "id": "thai", "name": "Thai" },
    { "id": "greek", "name": "Greek" },
    { "id": "spanish", "name": "Spanish" },
    { "id": "german", "name": "German" },
    { "id": "indian", "name": "Indian" },
    { "id": "vietnamese", "name": "Vietnamese" },
    { "id": "lebanese", "name": "Lebanese" },
    { "id": "turkish", "name": "Turkish" },
    { "id": "moroccan", "name": "Moroccan" },
    { "id": "ethiopian", "name": "Ethiopian" },
    { "id": "african", "name": "African" },
    { "id": "brazilian", "name": "Brazilian" },
    { "id": "argentinian", "name": "Argentinian" },
    { "id": "peruvian", "name": "Peruvian" },
    { "id": "comfort", "name": "Comfort" },
    { "id": "healthy", "name": "Healthy" },
    { "id": "vegetarian", "name": "Vegetarian" },
    { "id": "vegan", "name": "Vegan" },
    { "id": "pescatarian", "name": "Pescatarian" },
    { "id": "glutenfree", "name": "Gluten Free" },
    { "id": "dairyfree", "name": "Dairy Free" },
    { "id": "keto", "name": "Keto" },
    { "id": "paleo", "name": "Paleo" },
    { "id": "lowcarb", "name": "Lowcarb" },
    { "id": "budget", "name": "Budget" },
    { "id": "quick", "name": "Quick" },
    { "id": "slowcooker", "name": "Slow Cooker" },
    { "id": "grill", "name": "Grill" },
    { "id": "bake", "name": "Bake" },
    { "id": "raw", "name": "Raw" },
    { "id": "snack", "name": "Snack" },
    { "id": "dessert", "name": "Dessert" },
    { "id": "breakfast", "name": "Breakfast" },
    { "id": "lunch", "name": "Lunch" },
    { "id": "dinner", "name": "Dinner" },
    { "id": "holiday", "name": "Holiday" },
    { "id": "fusion", "name": "Fusion" },
    { "id": "traditional", "name": "Traditional" },
    { "id": "seasonal", "name": "Seasonal" },
    { "id": "onepot", "name": "Onepot" }
  ]
})