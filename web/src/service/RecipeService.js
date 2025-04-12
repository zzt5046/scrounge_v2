import { api } from '@/service/.service-registry'

export class RecipeService {
    getMeasurementUnits(system) {
        try {
            return api.get(`recipes/units/${system}`)
        } catch (e) {
            console.error(e)
            return e
        }
    }

    async getPublicRecipes() {
        try {
            return await api.get('recipes')
        } catch (e) {
            console.error(e)
            return e
        }
    }

    async getRecipe(id) {
        try {
            return await api.get(`recipes/${id}`)
        } catch (e) {
            console.error(e)
            return e
        }
    }

    async getAccountRecipes(accountId) {
        try {
            return await api.get(`recipes/all/${accountId}`)
        } catch (e) {
            console.error(e)
            return e
        }
    }

    async updateRecipe(recipeId) {
        try {
            return await api.put(`recipes/${recipeId}`)
        } catch (e) {
            console.error(e)
            return e
        }
    }

    async deleteRecipe(recipeId) {
        try {
            return await api.delete(`recipes/${recipeId}`)
        } catch (e) {
            console.error(e)
            return e
        }
    }
}
