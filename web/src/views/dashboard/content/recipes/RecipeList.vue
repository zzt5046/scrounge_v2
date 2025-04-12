<template>
    <div class="recipe-list">
        <div class="recipe-list-header">
            <h2>My Recipes</h2>
            <button id="add-recipe-button" class="btn btn-primary" @click="showAddRecipeModal = true">
                Add Recipe
            </button>
        </div>
        <div class="recipe-list-content">
            <RecipeCard
                class="recipe-card"
                v-for="recipeData in accountRecipes"
                :key="recipeData.id"
                :recipeName="recipeData.recipe.name"
                @select-recipe="selectRecipe(recipeData.recipe)"
            />
        </div>

        <!-- <RecipeCreateView v-show="showAddRecipeModal"/> -->
        <!-- <AppModal :show="showAddRecipeModal" :component="addRecipeForm" @close-modal="showAddRecipeModal = false"/> -->
    </div>
</template>

<script>
import { recipeService } from '@/service/.service-registry'
import { store } from '../../../../store'
import RecipeCard from './RecipeCard.vue'
import TextInput from '@/components/core/input/TextInput.vue'
import AppModal from '@/components/core/modal/AppModal.vue'
import { toRaw } from 'vue'

export default {
    name: 'RecipeList',
    components: {RecipeCard, TextInput, AppModal},

    data(){
        return {
            accountRecipes: [],
            showAddRecipeModal: false,
            addRecipeForm: import('./RecipeCreateView.vue'),
        }
    },

    created(){
        this.loadRecipes()
    },

    methods: {
        async loadRecipes(){
            const accountRecipes = await recipeService.getAccountRecipes(store.activeAccountId)
            accountRecipes.recipes?.forEach(recipeData => {
                this.accountRecipes.push({
                    id: recipeData.recipeId,
                    recipe: recipeData.recipe,
                })
            })
        },

        selectRecipe(recipe){
            this.$emit('select-recipe', toRaw(recipe))
        },
    },
}
</script>