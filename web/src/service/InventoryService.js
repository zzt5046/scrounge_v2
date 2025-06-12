import { api } from '@/service/.service-registry'

export class InventoryService {

    //returns inventory list
    async getInventory(accountId) {
        return await api.get(`inventories/${accountId}`)
    }

    async updateInventory(accountId, ingredients) {
        return await api.put(`inventories/${accountId}`, ingredients)
    }
}