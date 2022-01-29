package com.angeldearbo.marvelheroes.framework.data.datasources

import com.angeldearbo.marvelheroes.framework.data.model.MarvelResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelService {
    @GET("characters?limit=100")
    suspend fun getSuperheroes(): MarvelResponse

    @GET("characters/{characterId}")
    suspend fun getSuperheroDetails(
        @Path("characterId") characterId: Int
    ): MarvelResponse
}