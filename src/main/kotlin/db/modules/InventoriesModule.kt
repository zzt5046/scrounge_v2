package zjt.projects.db.modules

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import zjt.projects.AppContext
import zjt.projects.models.inventory.InventoryUpdateRequest

fun Application.inventoriesModule(){
    val inventoryService = AppContext.inventoryService

    val targetInventoryPath = "/inventories/{accountId}"
    val noIdFound = "No Account ID found"

    routing {

        // Get inventory by accountId
        get(targetInventoryPath) {
            val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException(noIdFound)
            inventoryService.findByAccountId(accountId)?.let { inventory ->
                call.respond(inventory.ingredients)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        // Update inventory by accountId
        put(targetInventoryPath) {
            try{
                val request = call.receive<InventoryUpdateRequest>()
                val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException(noIdFound)

                val inventory = inventoryService.findByAccountId(accountId)
                checkNotNull(inventory)

                val updatedInventory = inventory.copy(
                    ingredients = request.ingredients
                )

                inventoryService.updateByAccountId(accountId, updatedInventory)?.let {
                    call.respond(HttpStatusCode.OK, updatedInventory.ingredients)
                } ?: call.respond(HttpStatusCode.NotFound)
            }catch (e: Exception){
                when(e){
                    is NullPointerException,
                    is IllegalArgumentException,
                    is BadRequestException -> call.respond(HttpStatusCode.BadRequest)
                    is IllegalStateException -> call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        // Delete inventory
        delete(targetInventoryPath) {
            try {
                val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException(noIdFound)
                inventoryService.delete(accountId)?.let {
                    call.respond(HttpStatusCode.OK)
                } ?: call.respond(HttpStatusCode.NotFound)
            }catch (e: IllegalArgumentException){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}