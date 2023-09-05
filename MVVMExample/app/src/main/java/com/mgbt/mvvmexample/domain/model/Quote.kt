package com.mgbt.mvvmexample.domain.model

import com.mgbt.mvvmexample.data.database.entities.QuoteEntity
import com.mgbt.mvvmexample.data.network.model.QuoteModel

data class Quote(
    val quote: String,
    val author: String
)

//Mappers
fun QuoteModel.toDomain() = Quote(quote = quote, author = author)
fun QuoteEntity.toDomain() = Quote(quote = quote, author = author)