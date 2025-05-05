<template>
    <div class="recipes-panel">

        <!-- Default view -->
        <RecipeList 
            v-show="showDefault" 
            :recipes="accountRecipes"
            @select-recipe="selectRecipe" 
            @add-recipe="showRecipeCreateView"
        />
        <RecipeSearch 
            v-show="showDefault" 
            @select-recipe="selectRecipe"
        />

        <!-- Separate recipe management views -->
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
            accountRecipes: [],
            showDefault: true,
            createRecipe: false,
            inspectRecipe: false,
            editRecipe: false,
            selectedRecipe: null,
        }
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

    created(){
        this.loadRecipes()
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