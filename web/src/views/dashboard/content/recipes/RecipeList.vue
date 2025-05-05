<template>
    <div class="recipe-list" v-if="recipes">
        <div class="recipe-list-header">
            <h2>{{ $t('recipe.list.header') }}</h2>
            <p class="recipe-list-info" v-if="info" @click="this.$emit('clear-info')">{{ info }}</p>
            <button id="add-recipe-button" class="btn btn-primary" @click="showAddRecipeModal = true">
                {{ $t('recipe.actions.add') }}
            </button>
        </div>
        <div class="recipe-list-content">
            <RecipeCard
                class="recipe-card"
                v-for="recipeData in recipes"
                :key="recipeData.id"
                :recipeName="recipeData.recipe.name"
                @select-recipe="selectRecipe(recipeData)"
            />
        </div>
    </div>
</template>

<script>
import RecipeCard from './RecipeCard.vue'
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
    },
}
</script>