package com.mgbt.superheroapp.entities

import com.google.gson.annotations.SerializedName

data class SuperheroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superheroes: List<SuperheroItemResponse>
)

data class SuperheroItemResponse(
    @SerializedName("id") val idSuperhero: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val superheroImage: SuperheroImageResponse,
)

data class SuperheroImageResponse(
    @SerializedName("url") val url: String
)