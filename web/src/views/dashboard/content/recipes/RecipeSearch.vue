<template>
    <div class="recipe-search">
        <div class="recipe-search-header">
            <h2>Search Recipes</h2>
        </div>
        <div class="recipe-search-content">
            <TextInput id="recipe-search-input" v-model="searchQuery" placeholder="Search recipes..." type="search" @input="filterRecipeSearch"/>
            <RecipeCard
                class="recipe-card"
                v-for="recipeData in searchRecipes"
                :key="recipeData.id"
                :recipeName="recipeData.recipe.name"
                @select-recipe="selectRecipe(recipeData)"
            />
            <div v-if="showNoResultsNote" class="no-recipes-found">No recipes found.</div>
        </div>
    </div>
</template>
<script>
import { recipeService } from '@/service/.service-registry'
import { store } from '../../../../store'
import RecipeCard from './RecipeCard.vue'
import TextInput from '@/components/core/input/TextInput.vue'

export default {
    name: 'RecipeList',
    components: {RecipeCard, TextInput},

    data(){
        return {
            publicRecipes: [],
            searchRecipes: [],
            searchQuery: "",
        }
    },

    created(){
        this.loadRecipes()
        this.filterRecipeSearch()
    },

    computed: {
        showNoResultsNote(){
            return this.searchRecipes.length === 0 && this.searchQuery.length > 0
        },
    },

    methods: {
        async loadRecipes(){
            const publicRecipes = await recipeService.getPublicRecipes()
            publicRecipes.recipes?.forEach(recipeData => {
                this.publicRecipes.push({
                    id: recipeData.recipeId,
                    recipe: recipeData.recipe,
                })
            })

            this.searchRecipes.filter(recipeData => {
                //We only want to show public recipes that are not already in the account
                return !this.searchRecipes.some(recipeData => store.activeAccountId === recipeData.recipe.accountId)
            })
        },

        filterRecipeSearch(){
            this.searchRecipes = this.publicRecipes.filter(recipeData => {
                if(!this.searchQuery) return
                return recipeData.recipe?.name.toLowerCase().includes(this.searchQuery.toLowerCase())
            })
        },

        selectRecipe(recipeData){
            this.$emit('select-recipe', recipeData)
        },
    },
}
</script>