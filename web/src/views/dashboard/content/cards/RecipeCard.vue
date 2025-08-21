<template>
    <div class="recipe-card" @click="$emit('select-recipe')">
        <h5 class="card-title">{{ recipeName }}</h5>
    </div>
</template>

<script>
export default {
    name: 'RecipeCard',
    props: {
        recipeName: {
            type: String,
            required: true
        }
    },
}
</script>