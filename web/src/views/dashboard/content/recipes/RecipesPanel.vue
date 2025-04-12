<template>
    <div class="recipes-panel">
    <!-- <div class="recipes-panel-default" v-show="showDefault"> -->
        <RecipeList v-show="showDefault" @select-recipe="selectRecipe" @add-recipe="showRecipeCreateView"/>
        <RecipeSearch v-show="showDefault" @select-recipe="selectRecipe"/>
    <!-- </div> -->
    <!-- <div class="recipes-panel-create" v-show="createView"> -->
        <!-- <RecipeCreateView v-show="createView" @close="showDefaultView" /> -->
    <!-- </div> -->
    <!-- <div class="recipes-panel-inspect" v-show="inspectView"> -->
        <RecipeInspectView :recipe="selectedRecipe" v-show="inspectView" @close="showDefaultView" />
    </div>
<!-- </div> -->
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
    components: {RecipeList, RecipeSearch, RecipeCreateView, RecipeInspectView},

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
            selectedRecipe: null,
        }
    },

    computed: {
        defaultView(){
            return this.showDefault && !this.createRecipe && !this.inspectRecipe
        },
        createView(){
            return !this.showDefault && this.createRecipe && !this.inspectRecipe
        },
        inspectView(){
            return !this.showDefault && !this.createRecipe && this.inspectRecipe && this.selectedRecipe !== null
        },
        currentDashboardView(){
            return store.currentDashboardView
        },
    },

    methods: {
        showDefaultView(){
            this.showDefault = true
            this.createRecipe = false
            this.inspectRecipe = false
        },
        showRecipeCreateView(){
            this.showDefault = false
            this.createRecipe = true
            this.inspectRecipe = false
        },
        showRecipeInspectView(){
            this.showDefault = false
            this.createRecipe = false
            this.inspectRecipe = true
        },

        selectRecipe(recipe){
            this.selectedRecipe = toRaw(recipe)
            this.showRecipeInspectView()
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