import { store } from '../store'
import { 
    accountService, 
    recipeService, 
    inventoryService
 } from '@/service/.service-registry'

export class CacheService {


    // Set cache on login -----------------------------------------------------

    async cacheLoginData(loginResponse){
        Promise.all[
            this.cacheAccountData(loginResponse),
            this.cacheSettingsData(loginResponse),
            this.cacheRecipeData(loginResponse),
            this.cacheInventoryData(loginResponse)
        ]
    }

    async cacheAccountData(loginResponse){
        console.debug('Caching account data...')
        store.setActiveAccountId(loginResponse.accountId)
        store.setActiveAccount(loginResponse.account)
    }

    async cacheSettingsData(loginResponse){
        console.debug('Caching settings data...')
        store.setActiveAccountSettings(loginResponse.settings)
        const units = await recipeService.getMeasurementUnits(loginResponse.settings.MEASUREMENT_SYSTEM)
        store.setMeasurementUnits(units)
    }

    async cacheRecipeData(loginResponse){
        console.debug('Caching recipe data...')
        store.setActiveAccountRecipes(loginResponse.recipes)
    }

    async cacheInventoryData(loginResponse) {
        console.debug('Caching inventory data...')
        store.setActiveAccountInventory(loginResponse.inventory)
    }


    // Update cache ------------------------------------------------------------

    async refreshActiveAccount() {
        console.debug('Refreshing cached account data...')
        const account = await accountService.getAccount(store.activeAccountId)
        store.setActiveAccount(account)
    }

    async refreshActiveRecipes() {
        console.debug('Refreshing cached recipes...')
        const recipesResponse = await recipeService.getRecipes(store.activeAccountId)
        store.setActiveAccountRecipes(recipesResponse.recipes)
    }

    async refreshActiveInventory() {
        console.debug('Refreshing cached inventory...')
        const inventory = await inventoryService.getInventory(store.activeAccountId)
        store.setActiveAccountInventory(inventory.ingredients)
    }
}

