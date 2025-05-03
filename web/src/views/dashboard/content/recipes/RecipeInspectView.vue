<template>
    <div class="recipe-inspect" v-if="!editMode">
        <div v-if="userOwnsRecipe" class="recipe-inspect-actions">
            <button class="btn btn-primary" @click="showEditView">{{ $t('recipe.actions.edit') }}</button>
            <button class="btn btn-primary" @click="$emit('delete-recipe', recipe.id)">{{ $t('recipe.actions.delete') }}</button>
            <button class="btn btn-primary" @click="$emit('cancel-inspect')">{{ $t('actions.back') }}</button>
        </div>
        <div v-else class="recipe-inspect-actions">
            <button class="btn btn-primary" @click="$emit('favorite-recipe', recipe.id)">{{ $t('recipe.actions.save') }}</button>
            <button class="btn btn-primary" @click="$emit('back')">{{ $t('actions.back') }}</button>
        </div>

        <div class="recipe-inspect-content">
            <h2>{{ inspectRecipe.name }}</h2>
            <p v-if="!inspectRecipe.author?.length === 0">{{ $t('recipe.inspect.submitted') }} {{ inspectRecipe.author }}</p>
            <p>{{ inspectRecipe.description }}</p>

            <h3>{{ $t('recipe.ingredients') }}</h3>
            <p v-if="inspectRecipe.ingredients.length === 0">{{ $t('recipe.no_ingredients') }}</p>
            <div>
                <ul>
                    <li v-for="(ingredient, index) in inspectRecipe.ingredients" :key="index">
                        {{ ingredient.measurement.quantity }} {{ ingredient.measurement.measurementUnit }} {{ ingredient.name }}
                    </li>
                </ul>
            </div>

            <h3>{{ $t('recipe.directions') }}</h3>
            <p v-if="inspectRecipe.directions.length === 0">{{ $t('recipe.no_directions') }}</p>
            <div>
                <ol>
                    <li v-for="(direction, index) in inspectRecipe.directions" :key="index">
                        {{ direction }}
                    </li>
                </ol>
            </div>

            <h3>{{ $t('recipe.notes') }}</h3>
            <p v-if="inspectRecipe.notes?.length === 0">{{ $t('recipe.no_notes') }}</p>
            <p>
                {{ inspectRecipe.notes }}
            </p>
        </div>
    </div>

    <!-- EDIT VIEW -------------------------------------------------------------->

    <div class="recipe-edit" v-else>
        <div class="recipe-edit-content">
            <div class="recipe-info-edit">
                <h3>{{ $t('recipe.information') }}</h3>
                <TextInput id="edit-recipe-recipeName" v-model="editRecipe.name"/>
                <textarea  id="edit-recipe-recipeDesc" rows="5" cols="60" placeholder="Add notes here." v-model="editRecipe.description"></textarea>
            </div>

            <div class="recipe-ingredients-edit">
                <h3>{{ $t('recipe.ingredients') }}</h3>
                <p v-if="editRecipe.ingredients.length === 0">{{ $t('recipe.no_ingredients') }}</p>
                <div>
                    <ul>
                        <li v-for="(ingredient, index) in editRecipe.ingredients" :key="index">
                            <div class="recipe-edit-list-element">
                                <span>{{ ingredient.measurement.quantity }} {{ ingredient.measurement.measurementUnit }} {{ ingredient.name }}</span>
                                <img
                                    src="../../../../assets/icon/x-icon.png"
                                    class="remove-item-icon"
                                    :title="$t('actions.remove')"
                                    @click="removeIngredient(index)"
                                />
                            </div>
                        </li>
                    </ul>
                    <div class="recipe-edit-list-add">
                        <TextInput id="edit-recipe-newIngredientQuantity" :placeholder="$t('recipe.ingredient.quantity')" v-model="newIngredient.quantity"/>

                        <SelectInput
                            id="edit-recipe-newIngredientUnit"
                            :placeholder="$t('recipe.ingredient.unit')"
                            :options="units"
                            v-model="newIngredient.unit"
                        />
                        
                        <TextInput id="edit-recipe-newIngredientName" :placeholder="$t('recipe.ingredient.name')" v-model="newIngredient.name"/>
                        <button class="btn btn-primary" @click="addIngredient">{{ $t('recipe.ingredient.add') }}</button>
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
                <div class="recipe-edit-list-add">
                    <TextInput id="edit-recipe-newDirection" :placeholder="$t('recipe.add_direction')" v-model="newDirection"/>
                    <button class="btn btn-primary" @click="addDirection">{{ $t('recipe.direction.add') }}</button>
                </div>
            </div>
            <div class="recipe-notes-edit">
                <h3>{{ $t('recipe.notes') }}</h3>
                <textarea id="edit-recipe-notes" v-model="recipe.notes" rows="10" cols="100" :placeholder="$t('recipe.add_notes')"></textarea>
            </div>
            <div class="recipe-edit-actions">
                <button class="btn btn-primary" @click="saveRecipe">{{ $t('actions.save') }}</button>
                <button class="btn btn-primary" @click="cancelEditRecipe">{{ $t('actions.back') }}</button>
            </div>
        </div>
    </div>
</template>

<script>
import TextInput from '../../../../components/core/input/TextInput.vue'
import SelectInput from '../../../../components/core/input/SelectInput.vue'
import { store } from '../../../../store'
import { toRaw } from 'vue'

export default {
    name: 'RecipeInspectView',
    components: {
        TextInput,
        SelectInput
    },
    props: {
        recipe: {
            type: Object,
        }
    },

    data() {
        return {
            inspectRecipe: toRaw(this.recipe),
            editRecipe: {
                id: null,
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
        }
    },  
    
    computed: {
        userOwnsRecipe() {
            if(!this.recipe) return false
            return this.recipe.accountId === store.activeAccountId
        },
        units() {
            return toRaw(store.measurementUnits).map((unit) => {
                return {
                    id: unit,
                    name: unit,
                }
            })
        }
    },

    methods: {
        showEditView() {
            this.syncRecipe()
            this.editMode = true
        },
        cancelEditRecipe() {
            this.syncRecipe
            this.editMode = false
        },
        syncRecipe() {
            this.inspectRecipe = toRaw(this.recipe)
            this.editRecipe = toRaw(this.recipe)
        },
        saveRecipe() {
            this.editMode = false
        },
        addIngredient() {
            if (this.newIngredient.quantity && this.newIngredient.unit && this.newIngredient.name) {
                this.editRecipe.ingredients.push({
                    measurement: {
                        quantity: this.newIngredient.quantity,
                        measurementUnit: this.newIngredient.unit,
                    },
                    name: this.newIngredient.name,
                })
                this.newIngredient.quantity = null
                this.newIngredient.unit = null
                this.newIngredient.name = null
            }
        },
        removeIngredient(index) {
            this.editRecipe.ingredients.splice(index, 1)
        },
        
        addDirection() {
            if (this.newDirection) {
                this.editRecipe.directions.push(this.newDirection)
                this.newDirection = null
            }
        },
        removeDirection(index) {
            this.editRecipe.directions.splice(index, 1)
        },
    },
}
</script>
<style scoped>
    .remove-item-icon {
        cursor: pointer;
        margin-right: 1rem;
        margin-top: 0.25rem;
        width: 1rem;
        height: 1rem;
    }

    #edit-recipe-notes {
        margin-top: 1rem;
    }
</style>