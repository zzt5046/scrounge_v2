package zjt.projects.engine.modules

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import zjt.projects.db.services.ScroungeEngineService
import zjt.projects.models.engine.recipe.GenerateRecipesRequest

fun Application.smartRecipeModule(){

    val scroungeEngineService = ScroungeEngineService()

    routing {
        // Generate recipe from LLM
        post("/generate-recipe") {
            val request = call.receive<GenerateRecipesRequest>()
            val response = scroungeEngineService.generateRecipe(request)
            call.respond(HttpStatusCode.Created, response)
        }
    }
}