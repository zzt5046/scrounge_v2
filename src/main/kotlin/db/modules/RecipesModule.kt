package zjt.projects.db.modules

import zjt.projects.db.services.RecipeService
import zjt.projects.models.recipe.Recipe
import com.mongodb.client.MongoDatabase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.reflect.*
import zjt.projects.models.account.*
import zjt.projects.models.recipe.getMeasurementUnits

fun Application.recipesModule(db: MongoDatabase){
    val recipeService = RecipeService(db)

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
        get("/recipes/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            recipeService.getRecipeById(id)?.let { recipe ->
                call.respond(recipe)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        // Get recipes by accountId
        get("/recipes/all/{accountId}") {
            val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("No accountId found")
            call.respond(recipeService.getRecipesByAccountId(accountId))
        }

        get("/recipes/units/{system}") {
            val system = call.parameters["system"] ?: throw IllegalArgumentException("No measurement system found")
            val systemEnum: MeasurementSystem = MeasurementSystem.valueOf(system)
            call.respond(getMeasurementUnits(systemEnum))
        }

        // Update recipe
        put("/recipes/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            val recipe = call.receive<Recipe>()
            recipeService.updateRecipe(id, recipe)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }

        // Delete recipe
        delete("/recipes/{id}") {
            val id = call.parameters["id"] ?: throw IllegalArgumentException("No ID found")
            recipeService.deleteRecipe(id)?.let {
                call.respond(HttpStatusCode.OK)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
    }
}