package com.mgbt.mvvmexample.data

import com.mgbt.mvvmexample.data.database.dao.QuoteDao
import com.mgbt.mvvmexample.data.database.entities.QuoteEntity
import com.mgbt.mvvmexample.data.network.model.QuoteModel
import com.mgbt.mvvmexample.data.network.api.QuoteService
import com.mgbt.mvvmexample.domain.model.Quote
import com.mgbt.mvvmexample.domain.model.toDomain
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val api: QuoteService,
    private val quoteDao: QuoteDao
) {

    suspend fun getAllQuotesFromApi(): List<Quote> {
        val response: List<QuoteModel> = api.getQuotes()
        return response.map { it.toDomain() } //toDomain es un mapper definido en la data class Quote
    }

    suspend fun getAllQuotesFromDatabase(): List<Quote> {
        val response: List<QuoteEntity> = quoteDao.getAllQuotes()
        return response.map { it.toDomain() }
    }

    suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        quoteDao.insertAll(quotes)
    }

    suspend fun clearQuotes() {
        quoteDao.deleteAllQuotes()
    }
}