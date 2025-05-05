<template>
    <div class="recipe-create">
        <div class="recipe-create-content">
            <div class="recipe-info-create">
                <h3>{{ $t('recipe.information') }}</h3>

                <h5>{{ $t('recipe.name') }}</h5>
                <TextInput id="create-recipe-recipeName" v-model="recipe.name"/>

                <h5>{{ $t('recipe.description') }}</h5>
                <textarea  id="create-recipe-recipeDesc" rows="5" cols="60" :placeholder="$t('recipe.add_description')" v-model="recipe.description"></textarea>
            </div>

            <div class="recipe-ingredients-create">
                <h3>{{ $t('recipe.ingredients') }}</h3>
                <p v-if="recipe.ingredients.length === 0">{{ $t('recipe.no_ingredients') }}</p>
                <div>
                    <ul>
                        <li v-for="(ingredient, index) in recipe.ingredients" :key="index">
                            <div class="recipe-create-list-element">
                                <span>{{ ingredient.measurement.quantity }} {{ getUnitName(ingredient.measurement.measurementUnit) }} {{ ingredient.name }}</span>
                                <img
                                    src="../../../../assets/icon/x-icon.png"
                                    class="remove-item-icon"
                                    :title="$t('actions.remove')"
                                    @click="removeIngredient(index)"
                                />
                            </div>
                        </li>
                    </ul>
                    <div class="recipe-create-list-add">
                        <TextInput id="create-recipe-newIngredientQuantity" :placeholder="$t('recipe.ingredient.quantity')" v-model="newIngredient.quantity"/>

                        <SelectInput
                            id="create-recipe-newIngredientUnit"
                            :placeholder="$t('recipe.ingredient.unit')"
                            :options="units"
                            v-model="newIngredient.unit"
                        />
                        
                        <TextInput id="create-recipe-newIngredientName" :placeholder="$t('recipe.ingredient.name')" v-model="newIngredient.name"/>
                        <button class="btn btn-primary" @click="addIngredient">{{ $t('recipe.ingredient.add') }}</button>
                    </div>
                </div>
            </div>
            <div class="recipe-directions-create">
                <h3>{{ $t('recipe.directions') }}</h3>
                <p v-if="emptyDirections">{{ $t('recipe.no_directions') }}</p>
                <div v-else>
                    <ol>
                        <li v-for="(direction, index) in recipe.directions" :key="index">
                            <div class="recipe-create-list-element">
                                <span>{{ direction }}</span>
                                <img
                                    src="../../../../assets/icon/x-icon.png"
                                    class="remove-item-icon"
                                    :title="$t('actions.remove')"
                                    @click="removeDirection(index)"
                                />
                            </div>
                        </li>
                    </ol>
                </div>
                <div class="recipe-create-list-add">
                    <TextInput id="create-recipe-newDirection" :placeholder="$t('recipe.add_direction')" v-model="newDirection"/>
                    <button class="btn btn-primary" @click="addDirection">{{ $t('recipe.direction.add') }}</button>
                </div>
            </div>
            <div class="recipe-notes-create">
                <h3>{{ $t('recipe.notes') }}</h3>
                <textarea id="create-recipe-notes" v-model="recipe.notes" rows="10" cols="100" :placeholder="$t('recipe.add_notes')"></textarea>
            </div>
            <div class="recipe-public-create">
                <CheckboxField id="recipe-public-checkbox-create" :label="$t('recipe.make_public')" v-model="recipe.public"/>
            </div>
            <div class="recipe-create-actions">
                <button class="btn btn-primary" @click="saveRecipe">{{ $t('actions.save') }}</button>
                <button class="btn btn-primary" @click="back">{{ $t('actions.cancel') }}</button>
            </div>
            <div class="recipe-create-error" v-if="errorMessage">
                <p @click="errorMessage = null">{{ errorMessage }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import TextInput from '@/components/core/input/TextInput.vue'
import SelectInput from '@/components/core/input/SelectInput.vue'
import CheckboxField from '@/components/core/input/CheckboxField.vue'
import { recipeService } from '@/service/.service-registry'
import { store } from '../../../../store'

export default {
    name: 'RecipeCreateView',
    components: { TextInput, SelectInput, CheckboxField },
        
    data() {
        return {
            recipe: {
                accountId: store.activeAccountId,
                public: false,
                name: null,
                description: null,
                ingredients: [],
                directions: [],
                notes: null,
            },
            newIngredient: {
                quantity: null,
                unit: null,
                name: null,
            },
            newDirection: null,
            successMessage: null,
            errorMessage: null,
        }
    },

    computed: {
        emptyDirections() {
            return this.recipe.directions.length === 0
        },
        units() {
            return Array.from(Object.entries(store.measurementUnits))
                .map(([id, name]) => {
                    return { name, id }
                });
        },
    },

    methods: {
        back() {
            this.recipe = {
                accountId: store.activeAccountId,
                public: false,
                name: null,
                description: null,
                ingredients: [],
                directions: [],
                notes: null,
            },
            this.newIngredient = {
                quantity: null,
                unit: null,
                name: null,
            }
            this.$emit('back')
        },

        addIngredient() {
            if (this.newIngredient.quantity && this.newIngredient.unit && this.newIngredient.name) {
                this.recipe.ingredients.push({
                    measurement: {
                        quantity: this.newIngredient.quantity,
                        measurementUnit: this.newIngredient.unit,
                    },
                    name: this.newIngredient.name,
                })

                this.newIngredient = {
                    quantity: null,
                    unit: null,
                    name: null,
                }
            }
        },
        removeIngredient(index) {
            this.recipe.ingredients.splice(index, 1)
        },
        getUnitName(inputUnit) {
            return this.units.find((unit) => unit.id === inputUnit)?.name || ''
        },
        
        addDirection() {
            if (this.newDirection) {
                this.recipe.directions.push(this.newDirection)
                this.newDirection = null
            }
        },
        removeDirection(index) {
            this.recipe.directions.splice(index, 1)
        },

        async saveRecipe() {
            await recipeService.createRecipe(this.recipe).then(() => {
                this.$emit('create-recipe')
                this.back()
            }).catch((error) => {
                console.error('Error creating recipe:', error)
                this.errorMessage = this.$t('recipe.create.error')
            })
        },
    },
}

</script>