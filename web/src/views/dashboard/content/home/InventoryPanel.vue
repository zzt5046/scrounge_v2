<template>
    <div class="inventory-panel panel">
        <div class="inventory-panel-header">
            <h3> {{ $t('home.inventory.header') }} </h3>
            <TextInput class="inventory-search" v-model="inventorySearch" placeholder="Search" type="search" @input="filterInventorySearch"/>
            <button class="btn btn-primary add-btn" @click="addItem(inventoryAdd)" :disabled="!canAddItem">{{ $t('home.inventory.actions.add') }}</button>
            <TextInput class="inventory-add" v-model="inventoryAdd" placeholder="Enter item to add." type="search" @enter="addItem(inventoryAdd)"/>
        </div>
        <div class="inventory-panel-content panel">
            <span v-show="items.length == 0"> {{ $t('home.inventory.no-items') }} </span>
            <div class="inventory-panel-item" v-for="(item, index) in items" :key="index">
                <img src="../../../../assets/icon/x-icon.png" class="remove-item-icon nudge"
                    :title="$t('actions.remove')" @click="removeItem(index)" />
                <span> {{ item }} </span>
            </div>
        </div>
    </div>
</template>
<script>

import { inventoryService } from '@/service/.service-registry';
import { store } from '../../../../store';
import TextInput from '@/components/core/input/TextInput.vue';

export default {
    name: 'InventoryPanel',

    components: {
        TextInput,
    },

    data() {
        return {
            items: [],
            inventorySearch: null,
            inventoryAdd: null,
            loading: false,
        };
    },

    methods: {
        async loadInventory() {
            const inventory = await inventoryService.getInventory(store.activeAccountId);
            this.items = inventory || [];
            this.items.sort();
        },

        removeItem(index) {
            this.items.splice(index, 1)
            this.items.sort();
            this.updateInventory();
        },

        addItem(item) {
            if (!this.items.includes(item)) {
                this.items.push(item);
            }
            this.items.sort();
            this.updateInventory();
            this.inventoryAdd = null;
        },

        async updateInventory() {
            try {
                await inventoryService.updateInventory(store.activeAccountId, this.items);
            } catch (error) {
                console.error('Failed to update inventory:', error);
            }
        },

        filterInventorySearch(){
            this.items = this.items.filter(item => {
                if(!this.searchQuery) return false
                return item.toLowerCase().includes(this.inventorySearch.toLowerCase())
            })
        },
    },

    computed: {
        canAddItem() {
            return this.inventoryAdd && !this.items.includes(this.inventoryAdd);
        }
    },

    created() {
        this.loadInventory();
    }
};
</script>
<style scoped>
    .nudge {
        margin-right: 0.5rem;
        margin-bottom: 0.3rem;
    }

    .add-btn {
        margin-left: 2.5rem;
        height: 2.375rem;
    }
</style>