<template>
    <div class="dashboard-content home-panel">
        <div class="home-card-section">
            <!-- Welcome -->
            <HomeCard
                id="welcome-card"
                type="info"
                :label="$t('home-content.welcome-label')"
                :body="$t('home-content.welcome')"
                :closable="true"
            />
            <!-- Inventory items -->
            <HomeCard
                id="inventory-items-card"
                type="stat"
                :label="$t('home-content.inventory-items-label')"
                :stat="inventoryItemCount"
            />
            <!-- Recipes stored -->
            <HomeCard
                id="recipes-stored-card"
                type="stat"
                :label="$t('home-content.recipes-stored-label')"
                :stat="recipeCount"
            />
            <!-- Recipes favd -->
            <HomeCard
                id="recipes-favorited-card"
                type="stat"
                :label="$t('home-content.recipes-favorited-label')"
                :stat="recipeFavoriteCount"
            />
        </div>

        <InventoryPanel />
    </div>
</template>

<script>
import { store } from '../../../../store'
import { recipeService, inventoryService } from '@/service/.service-registry';
import HomeCard from '../cards/HomeCard.vue';
import InventoryPanel from './InventoryPanel.vue';
export default {
    name: 'HomePanel',
    components: {
        HomeCard,
        InventoryPanel,
    },

    data() {
        return {
            username: store.activeAccountUsername,
            recipeCount: null,
            inventoryItemCount: null,
        };
    },

    created() {
        this.countRecipes();
        this.countInventory();
    },

    computed: {
        recipeFavoriteCount() {
            return store.activeAccount.favoriteRecipes.length;
        },
    },

    methods: {
        async countRecipes() {
            const response = await recipeService.getAccountRecipes(store.activeAccountId);
            this.recipeCount = response?.recipes?.length;

        },
        async countInventory() {
            const response = await inventoryService.getInventory(store.activeAccountId);
            console.log('Inventory:', response);
            this.inventoryItemCount = response.inventory?.length;
        },
    },
};
</script>

<style scoped>
</style>