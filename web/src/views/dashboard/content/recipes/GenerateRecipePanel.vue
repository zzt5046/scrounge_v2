<template>
    <div class="generate-recipe-panel panel">
        <div class="generate-recipe-form">
            <div class="generate-recipe-header">
                <h3> {{ $t('recipe.generate.header') }} </h3>
                <button class="btn btn-secondary" @click="back"> {{ $t('actions.back') }} </button>
            </div>

            <div class="generate-recipe-content">

                <div class="generate-ingredients">
                    <h4> {{ $t('recipe.generate.ingredients') }} </h4>
                    <!-- <p> {{ $t('recipe.generate.select-ingredient') }} </p> -->
                    <div class="generate-add-ingredient">
                        <SelectInput id="generate-ingredient-select" :options="inventory" :placeholder="$t('recipe.generate.select-ingredient-placeholder')" v-model="ingredientSelect"/>
                        <button class="btn btn-primary add-btn ml-1" @click="addIngredient(false)" :disabled="!ingredientSelect">{{ $t('actions.add') }}</button>
                        <button class="btn btn-secondary ml-1" @click="ingredients.length = 0" :disabled="ingredients.length == 0">{{ $t('actions.clear') }}</button>
                    </div>
                    <TextInput id="generate-ingredient-custom" v-model="ingredientAdd" :placeholder="$t('recipe.generate.custom-ingredient-placeholder')" type="search" @enter="addIngredient(true)"/>
                </div>
                

                <div class="generate-preferences">
                    <h4> {{ $t('recipe.generate.preferences') }} </h4>
                    <!-- <p> {{ $t('recipe.generate.select-preference') }} </p> -->
                    <div class="generate-add-preference">
                        <SelectInput id="generate-preference-select" :options="premadePreferences" :placeholder="$t('recipe.generate.select-preference-placeholder')" v-model="preferenceSelect"/>
                        <button class="btn btn-primary add-btn ml-1" @click="addPreference(false)" :disabled="!preferenceSelect">{{ $t('actions.add') }}</button>
                        <button class="btn btn-secondary ml-1" @click="preferences.length = 0" :disabled="preferences.length == 0">{{ $t('actions.clear') }}</button>
                    </div>
                    <TextInput id="generate-preference-custom" v-model="preferenceAdd" :placeholder="$t('recipe.generate.custom-preference-placeholder')" type="search" @enter="addPreference(true)"/>
                </div>
                
                <div class="generate-count">
                    <h4> {{ $t('recipe.generate.count') }} </h4>
                    <p> {{ $t('recipe.generate.count-note') }} </p>
                    <TextInput id="generate-count-custom" v-model="count" type="number" min="1" max="5"/>
                </div>
            </div>

            <hr />
        
            <div class="generate-recipe-summary">

                <div class="generate-summary-ingredients">
                    <h5> {{ $t('recipe.generate.ingredients') }} </h5>
                    <div class="generate-summary-ingredients-list">
                        <div v-for="(item, index) in ingredients" :key="index">
                            <img src="../../../../assets/icon/x-icon.png" class="remove-item-icon nudge"
                                :title="$t('actions.remove')" @click="removeIngredient(index)" />
                            <span> {{ item }} </span>
                        </div>
                    </div>
                </div>

                <div class="generate-summary-preferences">
                    <h5> {{ $t('recipe.generate.preferences') }} </h5>
                    <div class="generate-summary-preferences-list">
                        <div v-for="(item, index) in preferences" :key="index">
                            <img src="../../../../assets/icon/x-icon.png" class="remove-item-icon nudge"
                                :title="$t('actions.remove')" @click="removePreference(index)" />
                            <span> {{ item }} </span>
                        </div>
                    </div>
                </div>

            </div>

            <LoadingButton 
                :label="$t('actions.generate')"
                @click="generateRecipe"
                :disabled="ingredients.length === 0 || preferences.length === 0"
                type="primary"
                :loading="generating"
            />
        </div>

        <div class="generate-recipe-results panel" v-if="generatedRecipes.length > 0">
            <div v-for="(recipe, index) in generatedRecipes" :key="index" class="generated-recipe">
                <div class="generate-results-header">
                    <h4> {{ recipe.name }} </h4>
                    <div class="generated-recipe-actions">
                        <LoadingButton 
                            :label="$t('actions.save')"
                            @click="saveRecipe(recipe)"
                            type="primary"
                            :loading="saving"
                        />
                        <button class="btn btn-secondary" @click="generatedRecipes.splice(index, 1)"> {{ $t('actions.dismiss') }} </button>
                    </div>
                </div>
                
                <p> {{ recipe.description }} </p>
                <ul>
                    <li v-for="(ingredient, i) in recipe.ingredients" :key="i">{{ ingredient }}</li>
                </ul>
                <ol>
                    <li v-for="(step, i) in recipe.directions" :key="i">{{ step }}</li>
                </ol>
                <p> {{ recipe.notes }} </p>

                <hr v-show="showRecipeSeperator(index)"/>
            </div>
            <span v-if="error">{{ this.error }}</span>
        </div>

        <LoadingSpinner :size="350" :lineSize="25" color="var(--vt-c-fawn)" v-else-if="generating" class="panel" style="margin: auto;"/>
    </div>
</template>
<script>

import { store } from '../../../../store';
import { smartFoodService, recipeService } from '@/service/.service-registry';
import SelectInput from '@/components/core/input/SelectInput.vue';
import TextInput from '@/components/core/input/TextInput.vue';
import LoadingButton from '@/components/core/button/LoadingButton.vue';
import LoadingSpinner from '@/components/core/animated/LoadingSpinner.vue';
import { sleep } from '../../../../functions';
import { notifications } from '../../../../notifications';

export default {
    name: 'GenerateRecipePanel',

    components: {
        SelectInput,
        TextInput,
        LoadingButton,
        LoadingSpinner,
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
            saving: false,
            generating: false,
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
            console.count('generate recipe called')
            if (this.generating) return;
            this.generatedRecipes = [];
            this.generating = true;
            await smartFoodService.generateRecipe(this.ingredients, this.preferences, this.count).then((response) => {
                console.log(response)
                this.generatedRecipes = response.recipes || [];
                if(this.generatedRecipes?.length === 0) {
                    notifications.showToast('An error has occurred. Please try different ingredients or preferences', 'error');
                } else {
                    this.error = null;
                }
            }).catch((error) => {
                console.error('Error generating recipe:', error);
                notifications.showToast('An error has occurred. Please try again later.', 'error');
            }).finally(() => {
                this.generating = false;
            });
            
        },

        async saveRecipe(recipe) {
            this.saving = true;

            var recipeRequest = {
                accountId: store.activeAccountId,
                name: recipe.name,
                description: recipe.description,
                ingredients: recipe.ingredients,
                directions: recipe.directions,
                notes: recipe.notes,
                public: false,
                author: 'Scrounge SmartFood',
                generated: true,
            }

            await sleep(500);
            await recipeService.saveGeneratedRecipe(recipeRequest).then(() => {
                console.log('Recipe saved successfully');
                this.$emit('recipeSaved', recipe);
                notifications.showToast('Recipe saved successfully!', 'success');
            }).catch((error) => {
                console.error('Error saving recipe:', error);
                notifications.showToast('Error saving recipe, please try again.', 'error');
            }).finally(() => {
                this.saving = false;
            });
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

        showRecipeSeperator(index) {
                return index !== this.generatedRecipes.length - 1;
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

.ml-1 {
    margin-left: 1rem;
}
</style>