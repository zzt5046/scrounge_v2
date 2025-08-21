import { api } from "./.service-registry";

export class SmartFoodService {

    async generateRecipe(ingredients, preferences, count) {

        const request = {
            ingredients: ingredients,
            preferences: preferences,
            count: count
        }
        return await api.post('smart-food/generate-recipe', request)
    }
}