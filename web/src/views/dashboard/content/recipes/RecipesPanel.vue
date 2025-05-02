<template>
    <div class="recipes-panel">

        <!-- Default view -->
        <RecipeList v-show="showDefault" @select-recipe="selectRecipe" @add-recipe="showRecipeCreateView"/>
        <RecipeSearch v-show="showDefault" @select-recipe="selectRecipe"/>

        <!-- Separate recipe management views -->
        <RecipeInspectView 
            v-show="inspectView"
            v-if="selectedRecipe"
            :recipe="selectedRecipe.recipe"  
            @edit-recipe="editSelectedRecipe" 
            @delete-recipe="deleteRecipe" 
            @favorite-recipe="favoriteRecipe"
            @cancel-inspect="showDefaultView" 
        />
    </div>
</template>

<script>
import RecipeList from './RecipeList.vue'
import RecipeCreateView from './RecipeCreateView.vue'
import RecipeInspectView from './RecipeInspectView.vue'
import RecipeSearch from './RecipeSearch.vue'
import { store } from '../../../../store'
import { toRaw } from 'vue'

    export default {
    name: 'RecipesPanel',
    components: {
        RecipeList, 
        RecipeSearch, 
        RecipeCreateView, 
        RecipeInspectView
    },

    props: {
        account:{
            type: Object,
            required: true,
        }
    },

    data(){
        return {
            showDefault: true,
            createRecipe: false,
            inspectRecipe: false,
            editRecipe: false,
            selectedRecipe: null,
        }
    },

    computed: {
        defaultView(){
            // return this.showDefault && !this.createRecipe && !this.inspectRecipe
            return this.showDefault
        },
        createView(){
            // return !this.showDefault && this.createRecipe && !this.inspectRecipe
            return this.createRecipe
        },
        inspectView(){
            // return !this.showDefault && !this.createRecipe && this.inspectRecipe && this.selectedRecipe !== null
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

        selectRecipe(recipeData){
            this.selectedRecipe = toRaw(recipeData)
            this.showRecipeInspectView()
        },

        editSelectedRecipe(){
            this.showRecipeEditView()
        },
        
        deleteRecipe(recipeId){
            recipeService.deleteRecipe(recipeId)

        },
        favoriteRecipe(recipeId){
            recipeService.favoriteRecipe(store.activeAccountId, recipeId)
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