<template>
    <div class="recipes-panel">

        <!-- Default view -->
        <RecipeList 
            v-show="showDefault" 
            :recipes="accountRecipes"
            :infoMessage="recipeListInfo"
            @clear-info="recipeListInfo = null"
            @select-recipe="selectRecipe" 
            @add-recipe="showRecipeCreateView"
        />
        <RecipeSearch 
            v-show="showDefault" 
            @select-recipe="selectRecipe"
        />

        <!-- Separate recipe management views -->
        <RecipeCreateView 
            v-show="createView" 
            @create-recipe="loadRecipes" 
            @back="showDefaultView"
        />

        <RecipeView 
            v-show="inspectView"
            v-if="selectedRecipe"
            :recipeData="selectedRecipe"  
            @edit-recipe="editSelectedRecipe" 
            @delete-recipe="deleteRecipe"
            @save-recipe="loadRecipes"
            @favorite-recipe="favoriteRecipe"
            @cancel-inspect="showDefaultView" 
        />
    </div>
</template>

<script>
import RecipeList from './RecipeList.vue'
import RecipeCreateView from './RecipeCreateView.vue'
import RecipeView from './RecipeView.vue'
import RecipeSearch from './RecipeSearch.vue'
import { store } from '../../../../store'
import { recipeService } from '@/service/.service-registry'


    export default {
    name: 'RecipesPanel',
    components: {
        RecipeList, 
        RecipeSearch, 
        RecipeCreateView, 
        RecipeView
    },

    props: {
        account:{
            type: Object,
            required: true,
        }
    },

    data(){
        return {
            interval: null,
            accountRecipes: [],
            showDefault: true,
            createRecipe: false,
            inspectRecipe: false,
            editRecipe: false,
            selectedRecipe: null,
            recipeListInfo: null,
        }
    },

    created(){
        this.loadRecipes()
        this.interval = setInterval(() =>{
            this.loadRecipes()
        }, 5000)
    },

    computed: {
        defaultView(){
            return this.showDefault
        },
        createView(){
            return this.createRecipe
        },
        inspectView(){
            return this.inspectRecipe && this.selectedRecipe !== null
        },
        editView(){
            return this.editRecipe
        },
        currentDashboardView(){
            return store.currentDashboardView
        },
    },

    methods: {
        showDefaultView(){
            this.selectedRecipe = null
            this.showDefault = true
            this.createRecipe = false
            this.inspectRecipe = false
            this.editRecipe = false
        },
        showRecipeCreateView(){
            this.showDefault = false
            this.createRecipe = true
            this.inspectRecipe = false
            this.editRecipe = false
        },
        showRecipeInspectView(){
            this.showDefault = false
            this.createRecipe = false
            this.inspectRecipe = true
            this.editRecipe = false
        },
        showRecipeEditView(){
            this.showDefault = false
            this.createRecipe = false
            this.inspectRecipe = false
            this.editRecipe = true
        },

        async loadRecipes(){
            const accountRecipes = await recipeService.getAccountRecipes(store.activeAccountId)
            this.accountRecipes = []
            accountRecipes.recipes?.forEach(recipeData => {
                this.accountRecipes.push({
                    id: recipeData.recipeId,
                    recipe: recipeData.recipe,
                })
            })
        },

        selectRecipe(recipeData){
            this.selectedRecipe = recipeData
            this.showRecipeInspectView()
        },

        editSelectedRecipe(){
            this.showRecipeEditView()
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