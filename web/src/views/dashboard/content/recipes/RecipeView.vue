<template>
    <div class="recipe-inspect" v-if="recipe && !editMode">
        <div class="recipe-update-success" v-if="successMessage">
            <p @click="successMessage = null">{{ successMessage }}</p>
        </div>
        <div class="recipe-inspect-actions">
            <button v-show="userOwnsRecipe" class="btn btn-primary" @click="showEditView">{{ $t('recipe.actions.edit') }}</button>
            <button v-show="userOwnsRecipe" class="btn btn-primary" @click="$emit('delete-recipe', recipeData.id)">
                {{ $t('recipe.actions.delete') }}
            </button>
            <button :class="{'btn btn-primary': true, 'favorites-button-show': isFavoriteRecipe, 'favorites-button-hide': !isFavoriteRecipe, }" @click="favoriteRecipe">
                {{ favoriteButtonLabel }}
            </button>
            <button class="btn btn-primary" @click="$emit('cancel-inspect')">{{ $t('actions.back') }}</button>
        </div>

        <div class="recipe-inspect-content">
            <h2>{{ recipe.name }}</h2>
            <h6 class="recipe-author"> 
                {{ $t('recipe.inspect.submitted_by') }} 
                <span class="recipe-author-link" @click="$emit('inspect-author', recipe.author)">{{ recipe.author }}</span>
            </h6>
            <p v-if="!recipe.author?.length === 0">{{ $t('recipe.inspect.submitted') }} {{ recipe.author }}</p>
            <p>{{ recipe.description }}</p>

            <h3>{{ $t('recipe.ingredients') }}</h3>
            <p v-if="recipe.ingredients.length === 0">{{ $t('recipe.no_ingredients') }}</p>
            <div>
                <ul>
                    <li v-for="(ingredient, index) in recipe.ingredients" :key="index">
                        {{ ingredient.measurement.quantity }} {{ getUnitName(ingredient.measurement.measurementUnit) }}
                        {{ ingredient.name }}
                    </li>
                </ul>
            </div>

            <h3>{{ $t('recipe.directions') }}</h3>
            <p v-if="recipe.directions.length === 0">{{ $t('recipe.no_directions') }}</p>
            <div>
                <ol>
                    <li v-for="(direction, index) in recipe.directions" :key="index">
                        {{ direction }}
                    </li>
                </ol>
            </div>

            <h3>{{ $t('recipe.notes') }}</h3>
            <p v-if="recipe.notes?.length === 0">{{ $t('recipe.no_notes') }}</p>
            <p>
                {{ recipe.notes }}
            </p>

            <h5>{{ $t('recipe.public') }}: 
                <span
                    :class="{ 'green': recipe.public == true, 'red': recipe.public == false }">
                    {{ uppercase(recipe.public) }}
                </span>
            </h5>
        </div>
    </div>

    <!-- EDIT VIEW -------------------------------------------------------------->

    <div class="recipe-edit" v-else-if="recipe && editMode">
        <div class="recipe-edit-content">
            <div class="recipe-info-edit">
                <h3>{{ $t('recipe.information') }}</h3>

                <h5>{{ $t('recipe.name') }}</h5>
                <TextInput id="edit-recipe-recipeName" v-model="editRecipe.name" />

                <h5>{{ $t('recipe.description') }}</h5>
                <TextAreaField id="edit-recipe-recipeDesc" rows="5" cols="60" :placeholder="$t('recipe.add_description')" v-model="editRecipe.description"/>
            </div>

            <div class="recipe-ingredients-edit">
                <h3>{{ $t('recipe.ingredients') }}</h3>
                <p v-if="editRecipe.ingredients.length === 0">{{ $t('recipe.no_ingredients') }}</p>
                <div>
                    <ul>
                        <li v-for="(ingredient, index) in editRecipe.ingredients" :key="index">
                            <div class="recipe-edit-list-element">
                                <span>{{ ingredient.measurement.quantity }} {{
                                    getUnitName(ingredient.measurement.measurementUnit) }} {{ ingredient.name }}</span>
                                <div class="recipe-list-element-icons">
                                    <img v-if="index > 0" src="../../../../assets/icon/up-arrow.png"
                                        class="up-arrow-icon" :title="$t('actions.move_up')"
                                        @click="moveIngredientUp(index)" />

                                    <img v-if="index < this.editRecipe.ingredients.length - 1"
                                        src="../../../../assets/icon/down-arrow.png" class="down-arrow-icon"
                                        :title="$t('actions.move_down')" @click="moveIngredientDown(index)" />

                                    <img src="../../../../assets/icon/x-icon.png" class="remove-item-icon"
                                        :title="$t('actions.remove')" @click="removeIngredient(index)" />
                                </div>
                            </div>
                        </li>
                    </ul>
                    <div class="recipe-edit-list-add seventy">
                        <div class="recipe-edit-list-ingredient-inputs">
                            <TextInput id="edit-recipe-newIngredientQuantity" class="newIngredientQuantity"
                                :placeholder="$t('recipe.ingredient.quantity')" v-model="newIngredient.quantity" :error="quantityError" :errorMessage="quantityErrorMessage" />

                            <SelectInput id="edit-recipe-newIngredientUnit" class="newIngredientUnit" :placeholder="$t('recipe.ingredient.unit')"
                                :options="units" v-model="newIngredient.unit" />

                            <TextInput id="edit-recipe-newIngredientName" class="newIngredientName" :placeholder="$t('recipe.ingredient.name')"
                                v-model="newIngredient.name" />
                        </div>
                        <button class="btn btn-primary" @click="addIngredient" :disabled="!newIngredientValid">{{ $t('recipe.ingredient.add')
                            }}</button>
                    </div>
                </div>
            </div>
            <div class="recipe-directions-edit">
                <h3>{{ $t('recipe.directions') }}</h3>
                <p v-if="emptyDirections">{{ $t('recipe.no_directions') }}</p>
                <div v-else>
                    <ol>
                        <li v-for="(direction, index) in editRecipe.directions" :key="index">
                            <div class="recipe-edit-list-element seventy">
                                <span>{{ direction }}</span>
                                <div class="recipe-list-element-icons">
                                    <img v-if="index > 0" src="../../../../assets/icon/up-arrow.png"
                                        class="up-arrow-icon" :title="$t('actions.move_up')"
                                        @click="moveDirectionUp(index)" />

                                    <img v-if="index < this.editRecipe.directions.length - 1"
                                        src="../../../../assets/icon/down-arrow.png" class="down-arrow-icon"
                                        :title="$t('actions.move_down')" @click="moveDirectionDown(index)" />

                                    <img src="../../../../assets/icon/x-icon.png" class="remove-item-icon"
                                        :title="$t('actions.remove')" @click="removeDirection(index)" />
                                </div>
                            </div>
                        </li>
                    </ol>
                </div>
                <div class="recipe-edit-list-add seventy">
                    <TextInput id="edit-recipe-newDirection" class="newDirection" :placeholder="$t('recipe.add_direction')"
                        v-model="newDirection" />
                    <button class="btn btn-primary" @click="addDirection" :disabled="!newDirectionValid">{{ $t('recipe.direction.add') }}</button>
                </div>
            </div>
            <div class="recipe-notes-edit">
                <h3>{{ $t('recipe.notes') }}</h3>
                <TextAreaField id="edit-recipe-notes" rows="10" cols="100" :placeholder="$t('recipe.add_notes')" v-model="editRecipe.notes"/>
            </div>
            <div class="recipe-public-edit">
                <CheckboxField id="recipe-public-checkbox-edit" :label="$t('recipe.make_public')"
                    v-model="editRecipe.public" />
            </div>
            <div class="recipe-edit-actions">
                <button class="btn btn-primary" @click="updateRecipe">{{ $t('actions.save') }}</button>
                <button class="btn btn-primary" @click="cancelEditRecipe">{{ $t('actions.cancel') }}</button>
            </div>
            <div class="recipe-update-error" v-if="errorMessage">
                <p @click="errorMessage = null">{{ errorMessage }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import TextInput from '../../../../components/core/input/TextInput.vue'
import SelectInput from '../../../../components/core/input/SelectInput.vue'
import CheckboxField from '../../../../components/core/input/CheckboxField.vue'
import TextAreaField from '../../../../components/core/input/TextAreaField.vue'
import { store } from '../../../../store'
import { recipeService, accountService } from '@/service/.service-registry'
import useVuelidate from '@vuelidate/core'
import { validationMessages } from '../../../../validations'

export default {
    name: 'RecipeView',
    components: {
        TextInput,
        SelectInput,
        CheckboxField,
        TextAreaField
    },
    props: {
        recipeData: {
            type: Object,
            required: true,
        },
        favorite: {
            type: Boolean,
            default: false
        }
    },

    created() {
        this.syncRecipe()
    },

    mounted(){
        if(this.isFavoriteRecipe){
            this.favoriteButtonLabel = this.$t('recipe.actions.unfavorite')
        }else{
            this.favoriteButtonLabel = this.$t('recipe.actions.favorite')
        }
    },

    data() {
        return {
            v$: useVuelidate(),
            recipe: null,
            editRecipe: {
                accountId: null,
                public: null,
                name: null,
                description: null,
                ingredients: [],
                directions: [],
                notes: null,
            },
            editMode: false,
            newIngredient: {
                quantity: null,
                unit: null,
                name: null,
            },
            quantityError: false,
            quantityErrorMessage: null,
            newDirection: null,
            successMessage: null,
            errorMessage: null,

            favoriteButtonLabel: null,
        }
    },  
    
    validations() {
        return {
            editRecipe: {
                name: { required: true },
                description: { required: true },
                ingredients: { required: true },
                directions: { required: true },
            }
        }
    },

    computed: {
        userOwnsRecipe() {
            if(!this.recipe) return false
            return this.recipe.accountId === store.activeAccountId
        },
        units() {
            return Array.from(Object.entries(store.measurementUnits))
                .map(([id, name]) => {
                    return { name, id }
                });
        },
        newIngredientValid() {
            return this.newIngredient.quantity && this.newIngredient.unit && this.newIngredient.name && !this.quantityError
        },
        newDirectionValid() {
            return this.newDirection
        },
        isFavoriteRecipe() {
            return store.activeAccount.favoriteRecipes.includes(this.recipeData.id)
        },
    },

    watch: {
        recipeData: {
            handler(newValue) {
                //sync data when recipeData changes upstream
                this.syncRecipe()
            },
            deep: true,
        },

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

    methods: {
        showEditView() {
            this.syncRecipe()
            this.editMode = true
        },
        cancelEditRecipe() {
            this.syncRecipe()
            this.editMode = false
        },

        // Ingredients --------------------------------------------------
        addIngredient() {
            this.editRecipe.ingredients.push({
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
        },
        moveIngredientUp(index) {
            const temp = this.editRecipe.ingredients[index]
            this.editRecipe.ingredients[index] = this.editRecipe.ingredients[index - 1]
            this.editRecipe.ingredients[index - 1] = temp
        },
        moveIngredientDown(index) {
            const temp = this.editRecipe.ingredients[index]
            this.editRecipe.ingredients[index] = this.editRecipe.ingredients[index + 1]
            this.editRecipe.ingredients[index + 1] = temp
        },
        removeIngredient(index) {
            this.editRecipe.ingredients.splice(index, 1)
        },
        // END Ingredients-----------------------------------------------

        getUnitName(inputUnit) {
            return this.units.find((unit) => unit.id === inputUnit)?.name || ''
        },
        
        // Directions ---------------------------------------------------
        addDirection() {
            if (this.newDirection) {
                this.editRecipe.directions.push(this.newDirection)
                this.newDirection = null
            }
        },
        moveDirectionUp(index) {
            const temp = this.editRecipe.directions[index]
            this.editRecipe.directions[index] = this.editRecipe.directions[index - 1]
            this.editRecipe.directions[index - 1] = temp
        },
        moveDirectionDown(index) {
            const temp = this.editRecipe.directions[index]
            this.editRecipe.directions[index] = this.editRecipe.directions[index + 1]
            this.editRecipe.directions[index + 1] = temp
        },
        removeDirection(index) {
            this.editRecipe.directions.splice(index, 1)
        },
        // END Directions-----------------------------------------------

        syncRecipe() {
            this.recipe = this.recipeData.recipe
            this.editRecipe = {
                accountId: this.recipe.accountId,
                name: this.recipe.name,
                public: this.recipe.public,
                author: store.activeAccount.userName,
                description: this.recipe.description,
                ingredients: this.recipe.ingredients.map((ingredient) => {
                    return {
                        measurement: {
                            quantity: ingredient.measurement.quantity,
                            measurementUnit: ingredient.measurement.measurementUnit,
                        },
                        name: ingredient.name,
                    }
                }),
                directions: this.recipe.directions.map((direction) => {
                    return direction
                }),
                notes: this.recipe.notes,
            }
        },

        async updateRecipe() {
            recipeService.updateRecipe(this.recipeData.id, this.editRecipe).then(() => {
                this.$emit('update-recipe')
                this.syncRecipe()
                this.successMessage = this.$t('recipe.update.success')
                this.editMode = false
            }).catch((error) => {
                console.error('Error updating recipe:', error)
                this.errorMessage = this.$t('recipe.update.error')
            })
        },

        uppercase(value) {
            return value?.toString().toUpperCase()
        },

        async favoriteRecipe(){
            var favorites = store.activeAccount.favoriteRecipes
            var addingFavorite = false

            if(favorites.includes(this.recipeData.id)){
                //if the recipe is already in the favorites, the user is trying to remove it
                favorites = favorites.filter((it) => it !== this.recipeData.id)
                addingFavorite = false
            }else{
                favorites.push(this.recipeData.id)
                addingFavorite = true
            }

            try{
                await accountService.updateAccount(store.activeAccountId, { favoriteRecipes: favorites })
                this.$emit('favorite-recipe')

                if(addingFavorite){
                    this.favoriteButtonLabel = this.$t('recipe.actions.unfavorite')
                }else{
                    this.favoriteButtonLabel = this.$t('recipe.actions.favorite')
                }
            }catch (error) {
                console.error('Error adding favorite recipe:', error);
                return;
            }
        },
    },
}
</script>
<style scoped>
    .green {
        color: green;
        font-weight: bold;
    }
    .red {
        color: red;
        font-weight: bold;
    }
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

    .recipe-edit-list-ingredient-inputs {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        margin-bottom: 1rem;
        max-width: 100% !important;
    }

    .seventy {
        width: 70%;
    }
</style>