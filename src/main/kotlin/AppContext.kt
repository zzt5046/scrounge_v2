package zjt.projects

import com.mongodb.client.MongoDatabase
import zjt.projects.db.operations.AccountService
import zjt.projects.db.services.InventoryService
import zjt.projects.db.services.RecipeService

object AppContext {

    lateinit var accountService: AccountService
    lateinit var recipeService: RecipeService
    lateinit var inventoryService: InventoryService

    fun initServices(db: MongoDatabase) {
        // Construct base services without references first
        accountService = AccountService()
        recipeService = RecipeService()
        inventoryService = InventoryService()

        // Inject dependencies afterward to avoid circular constructor dependency
        accountService.init(db, recipeService, inventoryService)
        recipeService.init(db, accountService)
        inventoryService.init(db)
    }
}