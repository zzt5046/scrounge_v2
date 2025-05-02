<template>
    <div class="recipe-inspect" v-if="recipe" v-show="!editMode">
        <div v-if="userOwnsRecipe" class="recipe-inspect-actions">
            <button class="btn btn-primary" @click="editRecipe">Edit Recipe</button>
            <button class="btn btn-primary" @click="$emit('delete-recipe', recipe.id)">Delete Recipe</button>
            <button class="btn btn-primary" @click="$emit('cancel-inspect')">Back</button>
        </div>
        <div v-else class="recipe-inspect-actions">
            <button class="btn btn-primary" @click="$emit('favorite-recipe', recipe.id)">Save Recipe</button>
            <button class="btn btn-primary" @click="$emit('back')">Back</button>
        </div>

        <div class="recipe-inspect-content">
            <h2>{{ recipe.name }}</h2>
            <p v-if="!recipe.author?.length === 0">Submitted by: {{ recipe.author }}</p>
            <p>{{ recipe.description }}</p>

            <h3>Ingredients</h3>
            <p v-if="recipe.ingredients.length === 0">No ingredients found.</p>
            <div>
                <ul>
                    <li v-for="(ingredient, index) in recipe.ingredients" :key="index">
                        {{ ingredient.measurement.quantity }} {{ ingredient.measurement.measurementUnit }} {{ ingredient.name }}
                    </li>
                </ul>
            </div>

            <h3>Directions</h3>
            <p v-if="recipe.directions.length === 0">No directions found.</p>
            <div>
                <ol>
                    <li v-for="(direction, index) in recipe.directions" :key="index">
                        {{ direction }}
                    </li>
                </ol>
            </div>

            <h3>Notes</h3>
            <p v-if="recipe.notes?.length === 0">No notes found.</p>
            <p>
                {{ recipe.notes }}
            </p>
        </div>
    </div>

    <!-- EDIT VIEW -------------------------------------------------------------->

    <div class="recipe-edit" v-show="editMode">
        <div class="recipe-edit-content">
            <div class="recipe-info-edit">
                <h3>Information</h3>
                <TextInput id="edit-recipe-recipeName" v-model="currentRecipe.name"/>
                <textarea  id="edit-recipe-recipeDesc" rows="5" cols="60" placeholder="Add notes here." v-model="currentRecipe.description"></textarea>
            </div>

            <div class="recipe-ingredients-edit">
                <h3>Ingredients</h3>
                <p v-if="currentRecipe.ingredients.length === 0">No ingredients found.</p>
                <div>
                    <ul>
                        <li v-for="(ingredient, index) in currentRecipe.ingredients" :key="index">
                            <div class="recipe-edit-list-element">
                                <span>{{ ingredient.measurement.quantity }} {{ ingredient.measurement.measurementUnit }} {{ ingredient.name }}</span>
                                <img
                                    src="../../../../assets/icon/x-icon.png"
                                    class="remove-item-icon"
                                    @click="removeIngredient(index)"
                                />
                            </div>
                        </li>
                    </ul>
                    <div class="recipe-edit-list-add">
                        <TextInput id="edit-recipe-newIngredientQuantity" placeholder="Quantity" v-model="newIngredient.quantity"/>

                        <SelectInput
                            id="edit-recipe-newIngredientUnit"
                            placeholder="Unit"
                            :options="units"
                            v-model="newIngredient.unit"
                        />
                        
                        <TextInput id="edit-recipe-newIngredientName" placeholder="Name" v-model="newIngredient.name"/>
                        <button class="btn btn-primary" @click="addIngredient">+ Add</button>
                    </div>
                </div>
            </div>
            <div class="recipe-directions-edit">
                <h3>Directions</h3>
                <p v-if="emptyDirections">No directions found.</p>
                <div v-else>
                    <ol>
                        <li v-for="(direction, index) in currentRecipe.directions" :key="index">
                            <div class="recipe-edit-list-element">
                                <span>{{ direction }}</span>
                                <img
                                    src="../../../../assets/icon/x-icon.png"
                                    class="remove-item-icon"
                                    @click="removeDirection(index)"
                                />
                            </div>
                        </li>
                    </ol>
                </div>
                <div class="recipe-edit-list-add">
                    <TextInput id="edit-recipe-newDirection" placeholder="Add Instruction" v-model="newDirection"/>
                    <button class="btn btn-primary" @click="addDirection">+ Add</button>
                </div>
            </div>
            <div class="recipe-notes-edit">
                <h3>Notes</h3>
                <textarea id="edit-recipe-notes" v-model="recipe.notes" rows="10" cols="100" placeholder="Add notes here."></textarea>
            </div>
            <div class="recipe-edit-actions">
                <button class="btn btn-primary" @click="saveRecipe">Save</button>
                <button class="btn btn-primary" @click="cancelEditRecipe">Back</button>
            </div>
        </div>
    </div>
</template>

<script>
import TextInput from '../../../../components/core/input/TextInput.vue'
import SelectInput from '../../../../components/core/input/SelectInput.vue'
import { store } from '../../../../store'
import Icon from '../../../../assets/icon/Icon.vue'
import { toRaw } from 'vue'

export default {
    name: 'RecipeInspectView',
    components: {
        TextInput,
        SelectInput,
        Icon,
    },
    props: {
        recipe: {
            type: Object,
        }
    },

    data() {
        return {
            currentRecipe: {
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
        editRecipe() {
            const recipeCopy = toRaw(this.recipe)
            this.currentRecipe = {
                id: recipeCopy.id,
                name: recipeCopy.name,
                description: recipeCopy.description,
                ingredients: recipeCopy.ingredients,
                directions: recipeCopy.directions,
                notes: recipeCopy.notes,
            }
            console.log(this.currentRecipe)
            this.editMode = true
        },
        cancelEditRecipe() {
            this.editMode = false
        },
        saveRecipe() {
            this.editMode = false
        },
        addIngredient() {
            if (this.newIngredient.quantity && this.newIngredient.unit && this.newIngredient.name) {
                this.currentRecipe.ingredients.push({
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
            this.currentRecipe.ingredients.splice(index, 1)
        },
        
        addDirection() {
            if (this.newDirection) {
                this.currentRecipe.directions.push(this.newDirection)
                this.newDirection = null
            }
        },
        removeDirection(index) {
            this.currentRecipe.directions.splice(index, 1)
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