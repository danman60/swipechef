package com.swipechef.app.data.remote

import com.google.gson.Gson
import com.swipechef.app.data.models.RecipeExtraction
import com.swipechef.app.data.models.ValidationResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OpenAIService {

    // TODO: Replace with your actual OpenAI API key
    private val apiKey = "YOUR_OPENAI_API_KEY"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    suspend fun extractRecipeFromImage(imageBase64: String): Result<RecipeExtraction> {
        return try {
            val requestBody = createVisionRequest(imageBase64)
            val request = Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                parseRecipeResponse(responseBody ?: "")
            } else {
                Result.failure(Exception("OpenAI API error: ${response.code} ${response.message}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun createVisionRequest(imageBase64: String): okhttp3.RequestBody {
        val prompt = """
            Extract recipe information from this image and return a JSON response with the following structure:
            {
                "title": "Recipe title",
                "summary": "Brief description (1-2 sentences)",
                "description": "Longer description if available",
                "ingredients": ["ingredient 1", "ingredient 2", ...],
                "steps": ["step 1", "step 2", ...],
                "cookTime": 30,
                "prepTime": 15,
                "servings": 4,
                "mealType": "Breakfast|Lunch|Dinner|Any",
                "tags": ["tag1", "tag2", ...],
                "validation": {
                    "missing": ["ingredients|directions|times|serves"],
                    "warnings": ["any warnings about incomplete data"]
                }
            }

            Rules:
            - Extract exact text, don't invent quantities
            - If a section is missing or unclear, include it in validation.missing
            - Parse time values like "2 hours", "30 min", "1 hr 15 min" to minutes
            - Infer 3-7 relevant tags from ingredients and title
            - Return only valid JSON, no other text
        """.trimIndent()

        val requestJson = """
            {
                "model": "gpt-4-vision-preview",
                "messages": [
                    {
                        "role": "user",
                        "content": [
                            {
                                "type": "text",
                                "text": "$prompt"
                            },
                            {
                                "type": "image_url",
                                "image_url": {
                                    "url": "data:image/jpeg;base64,$imageBase64"
                                }
                            }
                        ]
                    }
                ],
                "max_tokens": 1500,
                "temperature": 0.1
            }
        """.trimIndent()

        return requestJson.toRequestBody("application/json".toMediaType())
    }

    private fun parseRecipeResponse(responseBody: String): Result<RecipeExtraction> {
        return try {
            val openAIResponse = gson.fromJson(responseBody, OpenAIResponse::class.java)
            val content = openAIResponse.choices?.firstOrNull()?.message?.content
                ?: return Result.failure(Exception("No content in OpenAI response"))

            // Extract JSON from content (in case there's extra text)
            val jsonStart = content.indexOf("{")
            val jsonEnd = content.lastIndexOf("}") + 1
            val jsonContent = if (jsonStart >= 0 && jsonEnd > jsonStart) {
                content.substring(jsonStart, jsonEnd)
            } else {
                content
            }

            val extraction = gson.fromJson(jsonContent, RecipeExtraction::class.java)
            Result.success(extraction)
        } catch (e: Exception) {
            Result.failure(Exception("Failed to parse OpenAI response: ${e.message}"))
        }
    }

    // Data classes for OpenAI API response
    private data class OpenAIResponse(
        val choices: List<Choice>?
    )

    private data class Choice(
        val message: Message?
    )

    private data class Message(
        val content: String?
    )
}