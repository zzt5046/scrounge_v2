import { api } from '@/service/.service-registry'
import { store } from '../store'

export class InventoryService {

    getInventory(accountId) {
        return api.get(`inventories/${accountId}`)
    }

    updateInventory(accountId, ingredients) {
        api.put(`inventories/${accountId}`, { ingredients: ingredients })
            .then((response) => { return response })
        store.setActiveAccountInventory(ingredients)
    }

}