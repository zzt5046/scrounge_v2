package zjt.projects.db.modules

import zjt.projects.db.services.RecipeService
import zjt.projects.models.recipe.Recipe
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import zjt.projects.models.account.settings.MeasurementSystem
import zjt.projects.models.recipe.getMeasurementUnits

fun Application.recipesModule(db: MongoDatabase){
    val recipeService = RecipeService(db)

    val targetRecipePath = "/recipes/{id}"
    val noIdFound = "No ID found"

    routing {

        // Create recipe
        post("/recipes") {
            val request = call.receive<Recipe>()
            val id = recipeService.createRecipe(request)
            call.respond(HttpStatusCode.Created, id)
        }

        // Get all public recipes
        get("/recipes") {
            val response = recipeService.getPublicRecipes()
            call.respond(response)
        }

        // Get recipe by id
        get(targetRecipePath) {
            val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
            recipeService.getRecipeById(id)?.let { recipe ->
                call.respond(recipe)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        // Get recipes by accountId
        get("/recipes/all/{accountId}") {
            val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException(noIdFound)
            call.respond(recipeService.getRecipesByAccountId(accountId))
        }

        //get measurement units
        get("/recipes/units/{system}") {
            val system = call.parameters["system"] ?: throw IllegalArgumentException("No measurement system found")
            val systemEnum: MeasurementSystem = MeasurementSystem.valueOf(system)
            call.respond(getMeasurementUnits(systemEnum))
        }

        // Update recipe
        put(targetRecipePath) {
            val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
            val recipe = call.receive<Recipe>()
            recipeService.updateRecipe(id, recipe)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        // Delete recipe
        delete(targetRecipePath) {
            val id = call.parameters["id"] ?: throw IllegalArgumentException(noIdFound)
            recipeService.deleteRecipe(id)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
    }
}