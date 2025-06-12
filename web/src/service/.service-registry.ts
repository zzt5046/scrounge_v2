import { ApiService } from './ApiService'
import { AccountService } from './AccountService'
import { RecipeService } from './RecipeService'
import { InventoryService } from './InventoryService'

export const api = new ApiService()
export const accountService = new AccountService()
export const recipeService = new RecipeService()
export const inventoryService = new InventoryService()