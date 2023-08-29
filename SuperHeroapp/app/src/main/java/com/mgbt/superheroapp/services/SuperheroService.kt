package com.mgbt.superheroapp.services

import com.mgbt.superheroapp.entities.SuperheroDataResponse
import com.mgbt.superheroapp.entities.SuperheroDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SuperheroService {

    //suspend sirve para crear una función con corrutinas (asíncrona, que no trabaje en el hilo principal)
    @GET("/api/2043898309282505/search/{name}")
    suspend fun getSuperheroes(@Path("name") superheroName: String): Response<SuperheroDataResponse>

    @GET("/api/2043898309282505/{id}")
    suspend fun getSuperheroDetail(@Path("id") idSuperhero: String): Response<SuperheroDetailResponse>
}