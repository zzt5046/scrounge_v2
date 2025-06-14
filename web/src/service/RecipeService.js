import { api } from '@/service/.service-registry'

export class RecipeService {
    getMeasurementUnits(system) {
        return api.get(`recipes/units/${system}`)
    }

    async getPublicRecipes() {
        return await api.get('recipes')
    }

    async getRecipe(id) {
        return await api.get(`recipes/${id}`)
    }

    async getRecipes(accountId) {
        return await api.get(`recipes/all/${accountId}`)
    }

    async getFavoriteRecipes(accountId) {
        return await api.get(`recipes/favorite/${accountId}`)
    }

    async createRecipe(recipe) {
        return await api.post('recipes', recipe)
    }

    async updateRecipe(recipeId, newRecipe) {
        return await api.put(`recipes/${recipeId}`, newRecipe)
    }

    async deleteRecipe(recipeId) {
        return await api.delete(`recipes/${recipeId}`)
    }
}
