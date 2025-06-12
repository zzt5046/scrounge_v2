<template>
    <div class="inventory-panel panel">
        <div class="inventory-panel-header">
            <h4> {{ $t('home-content.inventory.header') }} </h4>
        </div>
        <div class="inventory-panel-content">
            <div class="inventory-panel-item panel" v-for="item in items" :key="'item-' + item">
                <span v-show="items.length == 0"> {{ $t('home-content.inventory.no-items') }} </span>
                <span> {{ item }} </span>
                <img src="../../../../assets/icon/x-icon.png" class="remove-item-icon"
                    :title="$t('actions.remove')" @click="removeItem(index)" />
            </div>
        </div>
    </div>
</template>
<script>

import { inventoryService } from '@/service/.service-registry';
import { store } from '../../../../store';

export default {
    name: 'InventoryPanel',
    data() {
        return {
            items: [],
            loading: false,
        };
    },

    watch: {
        items: {
            handler(newItems) {
                this.items = newItems.sort((a, b) => a.localeCompare(b));
            },
            deep: true
        }
    },

    methods: {
        async loadInventory() {
            const inventory = await inventoryService.getInventory(store.activeAccountId);
            this.items = inventory || [];
        },

        removeItem(index) {
            this.items.splice(index, 1)
            this.updateInventory();
        },

        addItem(item) {
            this.items.push(item);
            this.updateInventory();
        },

        async updateInventory() {
            try {
                await inventoryService.updateInventory(store.activeAccountId, this.items);
            } catch (error) {
                console.error('Failed to update inventory:', error);
            }
        }
    },

    created() {
        this.loadInventory();
    }
};
</script>