package com.mgbt.mvvmexample.data.network.api

import com.mgbt.mvvmexample.data.network.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApiClient {
    @GET("/.json")
    suspend fun getAllQuotes(): Response<List<QuoteModel>>
}