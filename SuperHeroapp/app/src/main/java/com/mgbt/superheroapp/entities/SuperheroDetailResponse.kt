package com.mgbt.superheroapp.entities

import com.google.gson.annotations.SerializedName

data class SuperheroDetailResponse(
    @SerializedName("name") val name: String,
    @SerializedName("powerstats") val powerstats: PowerStatsResponse,
    @SerializedName("image") val superheroImage: SuperheroImageDetail,
    @SerializedName("biography") val biography: Biography,
)

data class PowerStatsResponse(
    @SerializedName("intelligence") val intelligence: String,
    @SerializedName("strength") val strength: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("durability") val durability: String,
    @SerializedName("power") val power: String,
    @SerializedName("combat") val combat: String
)

data class SuperheroImageDetail(
    @SerializedName("url") val url: String
)

data class Biography(
    @SerializedName("full-name") val fullName: String,
    @SerializedName("publisher") val publisher: String,
)