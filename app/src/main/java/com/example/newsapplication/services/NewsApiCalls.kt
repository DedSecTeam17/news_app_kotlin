package com.example.newsapplication.services

import com.example.newsapplication.services.responses.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiCalls {
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): TopHeadlinesResponse


}