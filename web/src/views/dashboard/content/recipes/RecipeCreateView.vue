<template>
    <div class="recipe-create panel">
        <div class="recipe-create-content">
            <div class="recipe-create-header">
                <h2>{{ $t('recipe.create.header') }}</h2>
                <RequiredNote />
            </div>
            <div class="recipe-info-create">
                <h3 class="recipe-create-section-title">{{ $t('recipe.information') }}</h3>
                <TextInput id="create-recipe-recipeName" :label="$t('recipe.name')" v-model="recipe.name" :error="error(v$, 'recipe\.name')" :errorMessage="errorMessage(v$, 'recipe\.name')" required/>
                <TextAreaField  id="create-recipe-recipeDesc" :label="$t('recipe.description')" rows="5" cols="60" :placeholder="$t('recipe.add_description')" v-model="recipe.description" />
            </div>

            <div class="recipe-ingredients-create">
                <h3>{{ $t('recipe.ingredients') }}</h3>
                <p v-if="noIngredientsOnSubmit" class="input-error-text">{{ $t('recipe.add_ingredient_tip') }}</p>
                <ul>
                    <li v-for="(ingredient, index) in recipe.ingredients" :key="index">
                        <div class="recipe-create-list-element">
                            <span>{{ ingredient.measurement.quantity }} {{ getUnitName(ingredient.measurement.measurementUnit) }} {{ ingredient.name }}</span>
                            <div class="recipe-list-element-icons">
                                <img
                                v-if="index > 0"
                                src="../../../../assets/icon/up-arrow.png"
                                class="up-arrow-icon"
                                :title="$t('actions.move_up')"
                                @click="moveIngredientUp(index)"
                                />

                                <img
                                v-if="index < this.recipe.ingredients.length - 1"
                                src="../../../../assets/icon/down-arrow.png"
                                class="down-arrow-icon"
                                :title="$t('actions.move_down')"
                                @click="moveIngredientDown(index)"
                                />

                                <img
                                src="../../../../assets/icon/x-icon.png"
                                class="remove-item-icon"
                                :title="$t('actions.remove')"
                                @click="removeIngredient(index)"
                                />
                            </div>
                        </div>
                    </li>
                </ul>
                <div class="recipe-create-list-add">
                    <div class="recipe-create-list-ingredient-inputs">
                        <TextInput id="create-recipe-newIngredientQuantity" class="newIngredientQuantity" :placeholder="$t('recipe.ingredient.quantity')" v-model="newIngredient.quantity"
                        :error="quantityError" :errorMessage="quantityErrorMessage"/>

                        <SelectInput
                            id="create-recipe-newIngredientUnit"
                            class="newIngredientUnit"
                            :placeholder="$t('recipe.ingredient.unit')"
                            :options="units"
                            :showNullOption="true"
                            nullOptionLabel="No Unit"
                            v-model="newIngredient.unit"
                        />
                    
                        <TextInput id="create-recipe-newIngredientName" class="newIngredientName" :placeholder="$t('recipe.ingredient.name')" v-model="newIngredient.name"/>
                    </div>
                    <button class="btn btn-primary" @click="addIngredient" :disabled="!newIngredientValid">{{ $t('recipe.ingredient.add') }}</button>
                </div>
            </div>
            <div class="recipe-directions-create">
                <h3>{{ $t('recipe.directions') }}</h3>
                <p v-if="noDirectionsOnSubmit" class="input-error-text">{{ $t('recipe.add_direction_tip') }}</p>
                    <ol>
                        <li v-for="(direction, index) in recipe.directions" :key="index">
                            <div class="recipe-create-list-element">
                                <span>{{ direction }}</span>
                                <div class="recipe-list-element-icons">
                                    <img
                                    v-if="index > 0"
                                    src="../../../../assets/icon/up-arrow.png"
                                    class="up-arrow-icon"
                                    :title="$t('actions.move_up')"
                                    @click="moveDirectionUp(index)"
                                    />

                                    <img
                                    v-if="index < this.recipe.directions.length - 1"
                                    src="../../../../assets/icon/down-arrow.png"
                                    class="down-arrow-icon"
                                    :title="$t('actions.move_down')"
                                    @click="moveDirectionDown(index)"
                                    />

                                    <img
                                    src="../../../../assets/icon/x-icon.png"
                                    class="remove-item-icon"
                                    :title="$t('actions.remove')"
                                    @click="removeDirection(index)"
                                    />
                                </div>
                            </div>
                        </li>
                    </ol>
                <div class="recipe-create-list-add">
                    <TextInput id="create-recipe-newDirection" class="newDirection" :placeholder="$t('recipe.add_direction')" v-model="newDirection"/>
                    <button class="btn btn-primary" @click="addDirection" :disabled="!newDirectionValid">{{ $t('recipe.direction.add') }}</button>
                </div>
            </div>
            <div class="recipe-notes-create">
                <h3>{{ $t('recipe.notes') }}</h3>
                <TextAreaField id="create-recipe-notes" v-model="recipe.notes" :rows="10" :cols="100" :placeholder="$t('recipe.add_notes')" />
            </div>
            <div class="recipe-public-create">
                <CheckboxField id="recipe-public-checkbox-create" :label="$t('recipe.make_public')" v-model="recipe.public"/>
            </div>
            <div class="recipe-create-actions">
                <button class="btn btn-primary" @click="validateAndSubmit">{{ $t('actions.save') }}</button>
                <button class="btn btn-primary" @click="back">{{ $t('actions.cancel') }}</button>
            </div>
            <div class="recipe-create-error" v-if="errorText">
                <p @click="errorText = null">{{ errorText }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import FormsMixin from '../../../../mixins/FormsMixin.js'
import { useVuelidate } from '@vuelidate/core'
import { getValidations } from '@/validations'
import TextInput from '@/components/core/input/TextInput.vue'
import SelectInput from '@/components/core/input/SelectInput.vue'
import CheckboxField from '@/components/core/input/CheckboxField.vue'
import TextAreaField from '@/components/core/input/TextAreaField.vue'
import { recipeService } from '@/service/.service-registry'
import { store } from '../../../../store'
import RequiredNote from '../../../../components/core/input/RequiredNote.vue'
import { validationMessages } from '../../../../validations.js'

export default {
    name: 'RecipeCreateView',
    mixins: [FormsMixin], 
    components: { TextInput, SelectInput, CheckboxField, TextAreaField, RequiredNote },   

    data() {
        return {
            v$: useVuelidate({$scope: false}),
            recipe: {
                accountId: store.activeAccountId,
                public: false,
                author: store.activeAccount.userName,
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
            quantityError: false,
            quantityErrorMessage: null,
            ingredientAdded: false,
            newDirection: null,
            directionAdded: false,
            submitted: false,
            successText: null,
            errorText: null,
        }
    },

    validations() {
        return getValidations(this.$options.name)
    },

    watch: {
        'newIngredient.quantity': {
            handler(newValue) {
                const isNotNumber = isNaN(newValue) || isNaN(parseFloat(newValue))
                if(isNotNumber && newValue != null){
                    this.quantityError = true
                    this.quantityErrorMessage = validationMessages.nonNumber
                }else{
                    this.quantityError = false
                    this.quantityErrorMessage = null
                }
            },
        }
    },

    computed: {

        newIngredientValid() {
            return this.newIngredient.quantity && this.newIngredient.name && !this.quantityError
        },
        newDirectionValid() {
            return this.newDirection
        },
        emptyDirections() {
            return this.recipe.directions.length === 0
        },
        units() {
            return Array.from(Object.entries(store.measurementUnits))
                .map(([id, name]) => {
                    return { name, id }
                });
        },
        noIngredientsOnSubmit() {
            return this.recipe.ingredients.length === 0 && !this.ingredientAdded && this.submitted
        },
        noDirectionsOnSubmit() {
            return this.recipe.directions.length === 0 && !this.directionAdded && this.submitted
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
            this.submitted = false
            this.v$.$reset()
            this.$emit('back')
        },

        addIngredient() {
            this.ingredientAdded = true
            if (this.newIngredient.quantity && this.newIngredient.name) {

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
            this.quantityError = false,
            this.quantityErrorMessage = null
        },
        moveIngredientUp(index) {
            const temp = this.recipe.ingredients[index]
            this.recipe.ingredients[index] = this.recipe.ingredients[index - 1]
            this.recipe.ingredients[index - 1] = temp
        },
        moveIngredientDown(index) {
            const temp = this.recipe.ingredients[index]
            this.recipe.ingredients[index] = this.recipe.ingredients[index + 1]
            this.recipe.ingredients[index + 1] = temp
        },
        removeIngredient(index) {
            this.recipe.ingredients.splice(index, 1)
            if (this.recipe.ingredients.length === 0) {
                this.ingredientAdded = false
            }
        },
        getUnitName(inputUnit) {
            return this.units.find((unit) => unit.id === inputUnit)?.name || ''
        },
        
        addDirection() {
            this.directionAdded = true
            if (this.newDirection) {
                this.recipe.directions.push(this.newDirection)
                this.newDirection = null
            }
        },
        moveDirectionUp(index) {
            const temp = this.recipe.directions[index]
            this.recipe.directions[index] = this.recipe.directions[index - 1]
            this.recipe.directions[index - 1] = temp
        },
        moveDirectionDown(index) {
            const temp = this.recipe.directions[index]
            this.recipe.directions[index] = this.recipe.directions[index + 1]
            this.recipe.directions[index + 1] = temp
        },
        removeDirection(index) {
            this.recipe.directions.splice(index, 1)
            if (this.recipe.directions.length === 0) {
                this.directionAdded = false
            }
        },

        validateAndSubmit() {
            this.v$.$reset()
            this.submitted = true
            this.v$.$validate()
            if (!this.v$.$error) {
                this.saveRecipe()
            }else{
                console.log(this.v$)
            }
        },

        async saveRecipe() {
            await recipeService.createRecipe(this.recipe).then(() => {
                this.$emit('create-recipe')
                this.back()
            }).catch((error) => {
                console.error('Error creating recipe:', error)
                this.errorText = this.$t('recipe.create.error')
            })
        },
    },
}
</script>
<style scoped>
    /* 
    this shit combined with the stuff in recipes.css is kinda jumbled and hacky.
    got it to an ok point, might need refactored we'll see.. but beware
     */

    .input {
        margin-bottom: 1rem !important;
    }

    .newIngredientQuantity {
        width: 25%;
    }
    .newIngredientUnit {
        width: 20%;
    }
    .newIngredientName {
        width: 55%;
    }
    .newDirection {
        width: 80%;
    }

    .newIngredientQuantity,
    .newIngredientUnit,
    .newIngredientName,
    .newDirection {
        margin-right: 1rem !important
    }

    .recipe-create-list-ingredient-inputs{
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        margin-bottom: 1rem;
        max-width: 100% !important;
    }
</style>