<template>
    <div class="recipe-list" v-if="recipes">
        <div class="recipe-list-header">
            <h2 v-if="showFavorites">{{ $t('recipe.list.header_fav') }}</h2>
            <h2 v-else>{{ $t('recipe.list.header') }}</h2>
            <p class="recipe-list-info" v-if="info" @click="this.$emit('clear-info')">{{ info }}</p>
            <div id="recipe-list-header-actions">
                <button id="show-favorite-recipes-button" 
                    :class="{ 'btn btn-primary': true, 'favorites-button-show': showFavorites, 'favorites-button-hide': !showFavorites }" 
                    @click="toggleFavorites">
                    
                    {{ $t('recipe.actions.show_favorites') }}
                </button>
                <button id="add-recipe-button" class="btn btn-primary" :disabled="showFavorites" @click="$emit('add-recipe')">
                    {{ $t('recipe.actions.add') }}
                </button>
            </div>
        </div>
        <div class="recipe-list-content" v-if="recipes.length > 0">
            <RecipeCard
                class="recipe-card"
                v-for="recipeData in recipes"
                :key="recipeData.id"
                :recipeName="recipeData.recipe.name"
                @select-recipe="selectRecipe(recipeData)"
            />
        </div>
        <div class="recipe-list-content" v-else>
            <p v-if="showFavorites">{{ $t('recipe.list.no_recipes_fav') }}</p>
            <p v-else>{{ $t('recipe.list.no_recipes') }}</p>
        </div>
    </div>
</template>

<script>
import RecipeCard from '../cards/RecipeCard.vue'
import TextInput from '@/components/core/input/TextInput.vue'
import { toRaw } from 'vue'

export default {
    name: 'RecipeList',
    components: {RecipeCard, TextInput},

    props: {
        recipes: {
            type: Object,
            required: true,
        },
        infoMessage: {
            type: String,
            default: null,
        },
    },

    data(){
        return {
            accountRecipes: [],
            showFavorites: false,
            info: null,
        }
    },

    watch: {
        infoMessage: {
            immediate: true,
            handler(newValue) {
                this.info = newValue
            }
        }
    },

    methods: {
        selectRecipe(recipeData){
            this.$emit('select-recipe', toRaw(recipeData))
        },

        toggleFavorites(){
            this.showFavorites = !this.showFavorites
            if(this.showFavorites){
                this.$emit('show-favorites')
            }else{
                this.$emit('hide-favorites')
            }
        }
    },
}
</script>
<style scoped>
    .favorites-button-hide,
    .favorites-button-show {
        margin-right: 1rem;
    }
</style>