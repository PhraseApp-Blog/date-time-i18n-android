package com.example.assignment.interactors.home

import android.util.Log
import com.example.assignment.domain.model.Article
import com.example.assignment.domain.model.data.DataState
import com.example.assignment.network.NewsApiService
import com.example.assignment.network.handleUseCaseException
import com.example.assignment.network.model.toDomainList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext

class GetNewsList(private val newsApiService: NewsApiService) {

    companion object {
        const val DEFAULT_COUNTRY = "us"
    }

    fun execute(apiKey: String): Flow<DataState<List<Article>>> = flow {
        emit(DataState.loading())

        val newApiResponse = newsApiService.getNews(apiKey)
        withContext(Dispatchers.IO){
            Log.e("ahsguHDSA","HUJDHAKJ")
        }
        val articles = newApiResponse.articles.toDomainList()

        emit(DataState.success(articles))

    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}