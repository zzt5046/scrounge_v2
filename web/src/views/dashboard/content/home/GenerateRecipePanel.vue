<template>
    <div class="generate-recipe-panel panel">
        <div class="generate-recipe-form panel">
            <div class="generate-recipe-header">
                <h3> {{ $t('home.generate.header') }} </h3>
                <button class="btn btn-secondary" @click="back"> {{ $t('actions.back') }} </button>
            </div>

            <div class="generate-recipe-content">

                <div class="generate-ingredients">
                    <h4> {{ $t('home.generate.ingredients') }} </h4>
                    <p> {{ $t('home.generate.select-ingredient') }} </p>
                    <div class="generate-add-ingredient">
                        <SelectInput id="generate-ingredient-select" :options="inventory" :placeholder="$t('home.generate.ingredient-placeholder')" v-model="ingredientSelect"/>
                        <button class="btn btn-primary add-btn" @click="addIngredient(false)" :disabled="!ingredientSelect">{{ $t('home.inventory.actions.select') }}</button>
                    </div>
                    <TextInput if="generate-ingredient-custom" v-model="ingredientAdd" placeholder="Press Enter to add" type="search" @enter="addIngredient(true)"/>
                </div>
                

                <div class="generate-preferences">
                    <h4> {{ $t('home.generate.preferences') }} </h4>
                    <p> {{ $t('home.generate.select-preference') }} </p>
                    <div class="generate-add-preference">
                        <SelectInput id="generate-preference-select" :options="premadePreferences" :placeholder="$t('home.generate.preference-placeholder')" v-model="preferenceSelect"/>
                        <button class="btn btn-primary add-btn" @click="addPreference(false)" :disabled="!preferenceSelect">{{ $t('home.inventory.actions.select') }}</button>
                    </div>
                    <TextInput id="generate-preference-custom" v-model="preferenceAdd" placeholder="Press Enter to add" type="search" @enter="addPreference(true)"/>
                </div>
                
                <div class="generate-count">
                    <h4> {{ $t('home.generate.count') }} </h4>
                    <p> {{ $t('home.generate.count-note') }} </p>
                    <TextInput id="generate-count-custom" v-model="count" type="number" min="1" max="5"/>
                </div>
            </div>
            
            <h4> {{ $t('home.generate.summary') }} </h4>
            <div class="generate-recipe-summary">

                <div class="summary-ingredients">
                    <h5> {{ $t('home.generate.ingredients') }} </h5>
                    <div class="summary-ingredients-list">
                        <div v-for="(item, index) in ingredients" :key="index">
                            <img src="../../../../assets/icon/x-icon.png" class="remove-item-icon nudge"
                                :title="$t('actions.remove')" @click="removeIngredient(index)" />
                            <span> {{ item }} </span>
                        </div>
                    </div>
                </div>

                <div class="summary-preferences">
                    <h5> {{ $t('home.generate.preferences') }} </h5>
                    <div class="summary-preferences-list">
                        <div v-for="(item, index) in preferences" :key="index">
                            <img src="../../../../assets/icon/x-icon.png" class="remove-item-icon nudge"
                                :title="$t('actions.remove')" @click="removePreference(index)" />
                            <span> {{ item }} </span>
                        </div>
                    </div>
                </div>

            </div>

            <button class="btn btn-primary generate-btn" @click="generateRecipe" 
                :disabled="ingredients.length === 0 || preferences.length === 0" >
                {{ $t('actions.generate') }}
            </button> 
        </div>

        <div class="generate-recipe-results panel" v-if="generatedRecipes.length > 0">
            <div class="generate-results-header">
                <h3> {{ $t('home.generate.results') }} </h3>
                <button class="btn btn-secondary" @click="generatedRecipes = []">{{ $t('actions.dismiss') }}</button> 
            </div>
            
            <div v-for="(recipe, index) in generatedRecipes" :key="index" class="generated-recipe">
                <h4> {{ recipe.name }} </h4>
                <p> {{ recipe.description }} </p>
                <ul>
                    <li v-for="(ingredient, i) in recipe.ingredients" :key="i">{{ ingredient }}</li>
                </ul>
                <ol>
                    <li v-for="(step, i) in recipe.directions" :key="i">{{ step }}</li>
                </ol>
                <p> {{ recipe.notes }} </p>
            </div>
        </div>
        <div class="generate-recipe-results panel" v-if="error">
            <span>{{ this.error }}</span>
        </div>
    </div>
</template>
<script>

import { store } from '../../../../store';
import { smartFoodService } from '@/service/.service-registry';
import SelectInput from '@/components/core/input/SelectInput.vue';
import TextInput from '@/components/core/input/TextInput.vue';

export default {
    name: 'GenerateRecipePanel',

    components: {
        SelectInput,
        TextInput,
    },

    data() {
        return {
            inventory: [],
            ingredientSelect: null,
            ingredientAdd: null,
            ingredients: [],

            premadePreferences: [],
            preferenceSelect: null,
            preferenceAdd: null,
            preferences: [],

            count: 1,

            generatedRecipes: [],
            loading: false,
            error: null,
        };
    },

    mounted() {
        if(this.premadePreferences.length === 0) {
            const inventory = store.activeAccountInventory || [];
            this.inventory = inventory.map(item => {
                return {
                    id: item,
                    name: item,
                };
            });
            this.premadePreferences = store.recipePreferences
        }
    },

    methods: {
        async generateRecipe() {
            this.loading = true;
            const response = await smartFoodService.generateRecipe(this.ingredients, this.preferences, this.count)
            this.generatedRecipes = response.recipes || [];

            this.error = response.errorMessage || null;
            console.log('Error:', this.error);
            this.loading = false;
        },

        addIngredient(custom) {
            if(custom && this.ingredientAdd.length > 0) {
                this.ingredients.push(this.ingredientAdd)
                this.ingredientAdd = null;
            }else{
                this.ingredients.push(this.ingredientSelect)
                this.ingredientSelect = null;
            }
        },

        removeIngredient(index) {
            this.ingredients.splice(index, 1)
        },

        addPreference(custom) {
            if(custom && this.preferenceAdd.length > 0) {
                this.preferences.push(this.preferenceAdd)
                this.preferenceAdd = null;
            }else{
                this.preferences.push(this.preferenceSelect)
                this.preferenceSelect = null;
            }
        },

        removePreference(index) {
            this.preferences.splice(index, 1)
        },

        back() {
            this.$emit('back');
            this.ingredientSelect = null
            this.ingredientAdd = null
            this.ingredients = null
            this.preferenceSelect = null,
            this.preferenceAdd = null
            this.preferences = null
        },
    },
};
</script>
<style scoped>
.nudge {
    margin-right: 0.5rem;
    margin-bottom: 0.3rem;
}
</style>