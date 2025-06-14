import { api } from '@/service/.service-registry'
import { store } from '../store'

export class InventoryService {

    getInventory(accountId) {
        return api.get(`inventories/${accountId}`)
    }

    async updateInventory(accountId, ingredients) {
        // const newInventory = api.put(`inventories/${accountId}`, { ingredients: ingredients })
        // console.log(newInventory)
        return await api.put(`inventories/${accountId}`, { ingredients: ingredients })
    }

}