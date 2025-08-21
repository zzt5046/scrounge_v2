import { ApiService } from './ApiService'
import { AccountService } from './AccountService'
import { RecipeService } from './RecipeService'
import { InventoryService } from './InventoryService'
import { CacheService } from './CacheService'
import { SmartFoodService } from './SmartFoodService'

export const api = new ApiService()
export const accountService = new AccountService()
export const recipeService = new RecipeService()
export const inventoryService = new InventoryService()
export const cacheService = new CacheService()
export const smartFoodService = new SmartFoodService()