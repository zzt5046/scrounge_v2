package zjt.projects.db.services

import io.ktor.http.*
import kotlinx.serialization.json.Json
import zjt.projects.models.engine.recipe.GenerateRecipesRequest
import zjt.projects.models.engine.recipe.GenerateRecipesResponse
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ScroungeEngineService {

    fun generateRecipe(request: GenerateRecipesRequest): GenerateRecipesResponse {

        val httpRequest = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/generate-recipe"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(Json.encodeToString(request)))
            .build()

        val httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString())

        return Json.decodeFromString(httpResponse.body())
    }

    companion object {
        val client: HttpClient = HttpClient.newHttpClient()
        const val baseUrl = "http://localhost:8085"
    }
}