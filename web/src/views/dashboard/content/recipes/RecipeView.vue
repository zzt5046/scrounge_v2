<template>
    <div class="recipe-inspect" v-if="recipe && !editMode">
        <div class="recipe-update-success" v-if="successMessage">
            <p @click="successMessage = null">{{ successMessage }}</p>
        </div>
        <div v-if="userOwnsRecipe" class="recipe-inspect-actions">
            <button class="btn btn-primary" @click="showEditView">{{ $t('recipe.actions.edit') }}</button>
            <button class="btn btn-primary" @click="$emit('delete-recipe', recipeData.id)">{{
                $t('recipe.actions.delete') }}</button>
            <button class="btn btn-primary" @click="$emit('cancel-inspect')">{{ $t('actions.back') }}</button>
        </div>
        <div v-else class="recipe-inspect-actions">
            <button class="btn btn-primary" @click="$emit('favorite-recipe', recipeData.id)">{{
                $t('recipe.actions.save') }}</button>
            <button class="btn btn-primary" @click="$emit('cancel-inspect')">{{ $t('actions.back') }}</button>
        </div>

        <div class="recipe-inspect-content">
            <h2>{{ recipe.name }}</h2>
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
                <textarea id="edit-recipe-recipeDesc" rows="5" cols="60" :placeholder="$t('recipe.add_description')"
                    v-model="editRecipe.description"></textarea>
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
                    <div class="recipe-edit-list-add">
                        <TextInput id="edit-recipe-newIngredientQuantity"
                            :placeholder="$t('recipe.ingredient.quantity')" v-model="newIngredient.quantity" />

                        <SelectInput id="edit-recipe-newIngredientUnit" :placeholder="$t('recipe.ingredient.unit')"
                            :options="units" v-model="newIngredient.unit" />

                        <TextInput id="edit-recipe-newIngredientName" :placeholder="$t('recipe.ingredient.name')"
                            v-model="newIngredient.name" />
                        <button class="btn btn-primary" @click="addIngredient">{{ $t('recipe.ingredient.add')
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
                            <div class="recipe-edit-list-element">
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
                <div class="recipe-edit-list-add">
                    <TextInput id="edit-recipe-newDirection" :placeholder="$t('recipe.add_direction')"
                        v-model="newDirection" />
                    <button class="btn btn-primary" @click="addDirection">{{ $t('recipe.direction.add') }}</button>
                </div>
            </div>
            <div class="recipe-notes-edit">
                <h3>{{ $t('recipe.notes') }}</h3>
                <textarea id="edit-recipe-notes" v-model="editRecipe.notes" rows="10" cols="100"
                    :placeholder="$t('recipe.add_notes')"></textarea>
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
import { store } from '../../../../store'
import { recipeService } from '@/service/.service-registry'

export default {
    name: 'RecipeView',
    components: {
        TextInput,
        SelectInput,
        CheckboxField
    },
    props: {
        recipeData: {
            type: Object,
            required: true,
        }
    },

    mounted() {
        this.syncRecipe()
    },

    data() {
        return {
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
            newDirection: null,
            successMessage: null,
            errorMessage: null,
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
    },

    watch: {
        recipeData: {
            handler(newValue) {
                //sync data when recipeData changes upstream
                this.syncRecipe()
            },
            deep: true,
        },
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
            if (this.newIngredient.quantity && this.newIngredient.unit && this.newIngredient.name) {
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
</style>