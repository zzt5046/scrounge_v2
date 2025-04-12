<template>
    <div class="recipe-inspect-view" v-if="recipe">
        <div v-if="userOwnsRecipe">
            <button @click="$emit('edit-recipe', recipe)">Edit Recipe</button>
            <button @click="$emit('delete-recipe', recipe)">Delete Recipe</button>
        </div>
        <div v-else>
            <button @click="$emit('save-recipe', recipe)">Save Recipe</button>
        </div>
        <button @click="$emit('back')">Back</button>
        <div class="recipe-details">
            <h2>{{ recipe.name }}</h2>
            <p v-if="!recipe.author?.length === 0">Submitted by: {{ recipe.author }}</p>
            <p>{{ recipe.description }}</p>

            <h3>Ingredients</h3>
            <p v-if="recipe.ingredients.length === 0">No ingredients found.</p>
            <p>
                <ul>
                    <li v-for="(ingredient, index) in recipe.ingredients" :key="index">
                        {{ ingredient.measurement.quantity }} {{ ingredient.measurement.measurementUnit }} {{ ingredient.name }}
                    </li>
                </ul>
            </p>

            <h3>Directions</h3>
            <p v-if="recipe.directions.length === 0">No directions found.</p>
            <p>
                <ol>
                    <li v-for="(direction, index) in recipe.directions" :key="index">
                        {{ direction }}
                    </li>
                </ol>
            </p>

            <p v-if="recipe.notes?.length === 0">No notes found.</p>
            <p>
                {{ recipe.notes }}
            </p>
        </div>
    </div>
</template>

<script>
import { store } from '../../../../store'

export default {
    name: 'RecipeInspectView',
    props: {
        recipe: {
            type: Object,
        }
    },
    
    computed: {
        userOwnsRecipe() {
            if(!this.recipe) return false
            return this.recipe.accountId === store.activeAccountId
        },
    },

    methods: {
        async updateRecipe() {
            this.$emit('update-recipe', this.recipe.id)
        },
        async deleteRecipe() {
            this.$emit('delete-recipe', this.recipe.id)
        },
    },
}
</script>