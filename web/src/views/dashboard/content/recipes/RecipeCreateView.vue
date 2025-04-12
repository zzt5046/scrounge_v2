<template>
    <div class="recipe-inspect-view">
        <h2>New Recipe</h2>
        <div class="recipe-form">

            <h3>Information</h3>
            <TextInput id="recipe-name" :placeholder="$t('fields.recipe.name')" v-model="name" />
            <TextInput id="recipe-description" :label="$t('fields.recipe.desc')" v-model="description" />

            <div class="recipe-ingredients">
                <h3>Ingredients</h3>
                <div v-for="(ingredient, index) in ingredients" :key="index" class="ingredient-item">
                    {{ ingredient.quantity + " " + ingredient.measurement + " " + ingredient.name }}
                    <button @click="removeIngredient(index)" class="btn btn-secondary">Remove</button>
                </div>
                <TextInput :label="$t('fields.recipe.quantity')" v-model="ingredient.quantity" />
                <SelectInput
                    id="recipe-ingredient-measurement"
                    :options="measurements"
                    v-model="ingredient.measurement"
                    required
                />
                <TextInput :label="$t('fields.ingredient')" v-model="ingredient.name" />
                <button @click="addIngredient">+ Add</button>
            </div>

            <div class="recipe-directions">
                <h3>Directions</h3>
                <div v-for="(direction, index) in directions" :key="index" class="direction-item">
                    {{ (index + 1) + ". " + direction }}
                    <button @click="removeDirection(index)" class="btn btn-secondary">Remove</button>
                </div>
                <TextInput :label="$t('fields.recipe.new-direction')" v-model="direction" />
                <button @click="addDirection">+ Add</button>
            </div>
        </div>
    </div>
</template>

<script>
import TextInput from '@/components/core/input/TextInput.vue'
import { recipeService } from '@/service/.service-registry'
import { store } from '../../../../store'

export default {
    name: 'RecipeCreateView',
    components: { TextInput },
        
    data() {
        return {
            accountId: store.activeAccountId,
            measurements: [],

            name: null,
            description: null,

            ingredient:{
                quantity: null,
                measurement: null,
                name: null,
            },
            ingredients: [],

            direction: null,
            directions: [],
        }
    },

    created() {
        this.loadMeasurements()
    },
    methods: {
        async loadMeasurements() {
            const measurementSystem = store.activeAccountSettings.MEASUREMENT_SYSTEM
            const measurementUnits = await recipeService.getMeasurementUnits(measurementSystem)
            this.measurements = measurementUnits
        },

        addIngredient() {
            this.ingredients.push({ 
                    quantity: this.ingredient.quantity, 
                    measurement: this.ingredient.measurement, 
                    name: this.ingredient.name 
                })
            this.ingredient.quantity = null
            this.ingredient.measurement = null
            this.ingredient.name = null
        },
        removeIngredient(index) {
            this.ingredients.remove(index)
        },

        addDirection() {
            this.directions.push(this.direction)
            this.direction = null
        },
        removeDirection(index) {
            this.directions.remove(index)
        },

        saveRecipe(){
            //UNFINISHED
            const recipe = {
                name: this.name,
                description: this.description,
                ingredients: this.ingredients,
                directions: this.directions,
            }
            recipeService.createRecipe(this.accountId, recipe).then(() => {
                this.$emit('recipe-created', recipe)
            }).catch((error) => {
                console.error('Error creating recipe:', error)
            })
        }
    },
}

</script>