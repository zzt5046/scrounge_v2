<template>
    <div class="recipes-panel">

        <!-- Default view -->
        <RecipeList 
            v-show="showDefault" 
            :recipes="featuredRecipes"
            :infoMessage="recipeListInfo"
            @clear-info="recipeListInfo = null"
            @select-recipe="selectRecipe" 
            @add-recipe="showRecipeCreateView"
            @show-favorites="showFavoriteRecipes"
            @hide-favorites="showAccountRecipes"
            @generate-recipe="showGenerateRecipeView"
        />
        <RecipeSearch 
            v-show="showDefault" 
            @select-recipe="selectRecipe"
        />

        <!-- Separate recipe management views -->
        <RecipeCreateView 
            v-show="createRecipe" 
            @create-recipe="loadRecipes" 
            @back="showDefaultView"
        />

        <RecipeView 
            v-show="inspectRecipe"
            v-if="selectedRecipe"
            :recipeData="selectedRecipe"
            @edit-recipe="editSelectedRecipe" 
            @delete-recipe="deleteRecipe"
            @save-recipe="loadRecipes"
            @update-recipe="loadRecipes"
            @favorite-recipe="$emit('refresh-account')"
            @cancel-inspect="showDefaultView" 
        />

        <GenerateRecipePanel v-if="generateRecipe" v-show="generateRecipe" @back="showDefaultView"/>
    </div>
</template>

<script>
import RecipeList from './RecipeList.vue'
import RecipeCreateView from './RecipeCreateView.vue'
import RecipeView from './RecipeView.vue'
import RecipeSearch from './RecipeSearch.vue'
import GenerateRecipePanel from './GenerateRecipePanel.vue'
import { store } from '../../../../store'
import { dashboardState } from '../../dashboardState.js'
import { watch } from 'vue'
import { recipeService } from '@/service/.service-registry'


    export default {
    name: 'RecipesPanel',
    components: {
        RecipeList, 
        RecipeSearch, 
        RecipeCreateView, 
        RecipeView,
        GenerateRecipePanel
    },

    props: {
        account:{
            type: Object,
            required: true,
        }
    },

    data(){
        return {
            featuredRecipes: [],
            accountRecipes: [],
            favoriteRecipes: [],
            displayFavoriteRecipes: false,

            showDefault: true,
            createRecipe: false,
            inspectRecipe: false,
            generateRecipe: false,

            selectedRecipe: null,
            recipeListInfo: null,
        }
    },

    async created(){
        // make sure we're showing account recipes after creating
        await this.showAccountRecipes()
    },

    mounted(){
        watch(
            () => dashboardState.activeSection,
            (newVal, oldVal) => {
                this.showDefaultView()
            },
        )
    },

    methods: {
        showDefaultView(){
            this.selectedRecipe = null
            this.showDefault = true
            this.createRecipe = false
            this.inspectRecipe = false
            this.generateRecipe = false
            this.showAccountRecipes()
        },
        showRecipeCreateView(){
            this.showDefault = false
            this.createRecipe = true
            this.inspectRecipe = false
            this.generateRecipe = false
        },
        showRecipeInspectView(){
            this.showDefault = false
            this.createRecipe = false
            this.inspectRecipe = true
            this.generateRecipe = false
        },
        showGenerateRecipeView(){
            this.selectedRecipe = null
            this.showDefault = false
            this.createRecipe = false
            this.inspectRecipe = false
            this.generateRecipe = true
        },

        async loadRecipes(){
            const accountRecipes = await recipeService.getRecipes(store.activeAccountId)
            this.accountRecipes = []
            accountRecipes.recipes?.forEach(recipeData => {
                this.accountRecipes.push({
                    id: recipeData.recipeId,
                    recipe: recipeData.recipe,
                })
                // make sure to update the selected recipe so changes can propogate down to recipe view
                if(recipeData.recipeId == this.selectedRecipe?.id){
                    this.selectedRecipe = recipeData
                }
            })
        },

        async loadFavoriteRecipes(){
            const favoriteRecipes = await recipeService.getFavoriteRecipes(store.activeAccountId)
            this.favoriteRecipes = []
            favoriteRecipes.recipes?.forEach(recipeData => {
                this.favoriteRecipes.push({
                    id: recipeData.recipeId,
                    recipe: recipeData.recipe,
                })
                //make sure to update the selected recipe so changes can propogate down to recipe view
                if(recipeData.recipeId == this.selectedRecipe?.id){
                    this.selectedRecipe = recipeData
                }
            })
        },

        async showFavoriteRecipes(){
            await this.loadFavoriteRecipes()
            this.featuredRecipes = this.favoriteRecipes
        },

        async showAccountRecipes(){
            await this.loadRecipes()
            this.featuredRecipes = this.accountRecipes
        },

        async reloadAllRecipes(){
            await this.loadRecipes()
            await this.loadFavoriteRecipes()
        },

        selectRecipe(recipeData){
            this.selectedRecipe = recipeData
            this.showRecipeInspectView()
        },

        async deleteRecipe(id){
            await recipeService.deleteRecipe(id)
            this.recipeListInfo = this.$t('recipe.list.deleted') + this.getRecipeName(id)
            this.loadRecipes()
            this.showDefaultView()
        },
        getRecipeName(id){
            const recipeData = this.accountRecipes.find(recipeData => recipeData.id === id)
            return recipeData ? recipeData.recipe.name : null
        },
    },

    watch: {
        currentDashboardView(newView){
            if(newView !== 'Recipes'){
                this.showDefaultView()
            }
        },
    },
}

</script>