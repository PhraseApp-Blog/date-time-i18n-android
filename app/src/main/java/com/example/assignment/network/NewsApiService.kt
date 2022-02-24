package com.example.assignment.network

import com.example.assignment.network.model.NewsApiResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsApiService {

    @GET("everything?q=newyork")
    suspend fun getNews(
        @Query("apiKey") apiKey: String,
        @Query("sortBy") sortBy: String = "publishedAt",
    ): NewsApiResponseDto

}