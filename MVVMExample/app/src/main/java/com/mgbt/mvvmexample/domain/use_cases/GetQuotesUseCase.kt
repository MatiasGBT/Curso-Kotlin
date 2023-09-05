package com.mgbt.mvvmexample.domain.use_cases

import com.mgbt.mvvmexample.data.QuoteRepository
import com.mgbt.mvvmexample.data.database.entities.toDatabase
import com.mgbt.mvvmexample.domain.model.Quote
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {

    //Este caso de uso se va a llamar solo una vez cuando se inicie la app.
    //Si la llamada a la API falla, se trata de recuperar algunas quotes cacheadas en la BD.
    suspend operator fun invoke(): List<Quote> {
        val quotes = repository.getAllQuotesFromApi()

        return if (quotes.isNotEmpty()) {
            repository.clearQuotes()
            repository.insertQuotes(quotes.map { it.toDatabase() }) //toDatabase() mapper creado en QuoteEntity
            quotes
        } else {
            repository.getAllQuotesFromDatabase()
        }
    }
}