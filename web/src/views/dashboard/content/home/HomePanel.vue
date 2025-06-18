<template>
    <div class="dashboard-content home-panel">
        <div class="home-card-section">
            <!-- Welcome -->
            <HomeCard
                id="welcome-card"
                type="info"
                :label="$t('home.welcome-label')"
                :body="$t('home.welcome')"
                :closable="true"
            />
            <!-- Inventory items -->
            <HomeCard
                id="inventory-items-card"
                type="stat"
                :label="$t('home.inventory-items-label')"
                :statNum="inventoryCount"
            />
            <!-- Recipes stored -->
            <HomeCard
                id="recipes-stored-card"
                type="stat"
                :label="$t('home.recipes-stored-label')"
                :statNum="recipeCount"
            />
            <!-- Recipes favd -->
            <HomeCard
                id="recipes-favorited-card"
                type="stat"
                :label="$t('home.recipes-favorited-label')"
                :statNum="recipeFavoriteCount"
            />
        </div>

        <InventoryPanel v-if="!showGenerateRecipe" @generate="showGenerateRecipe = true"/>
        <GenerateRecipePanel v-else @back="showGenerateRecipe = false"/>
        
    </div>
</template>

<script>
import { store } from '../../../../store'
import HomeCard from '../cards/HomeCard.vue';
import InventoryPanel from './InventoryPanel.vue';
import GenerateRecipePanel from './GenerateRecipePanel.vue';

export default {
    name: 'HomePanel',
    components: {
        HomeCard,
        InventoryPanel,
        GenerateRecipePanel,
    },

    data() {
        return {
            recipeCount: null,
            inventoryCount: null,
            showGenerateRecipe: false,
        };
    },

    computed: {
        recipeCount() {
            return store.activeAccountRecipes.length;
        },
        inventoryCount() {
            return store.activeAccountInventory.length;
        },
        recipeFavoriteCount() {
            return store.activeAccount.favoriteRecipes.length;
        },
        userName() {
            return store.activeAccount.username;
        },
    }
};
</script>