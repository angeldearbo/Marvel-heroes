package com.angeldearbo.marvelheroes.data.repository

import com.angeldearbo.marvelheroes.data.datasources.RemoteDataSource
import com.angeldearbo.marvelheroes.data.repository.exceptions.AuthorizationError
import com.angeldearbo.marvelheroes.data.repository.exceptions.Fatal
import com.angeldearbo.marvelheroes.data.repository.exceptions.NetworkError
import com.angeldearbo.marvelheroes.data.repository.exceptions.ServerError
import com.angeldearbo.marvelheroes.domain.Hero
import retrofit2.HttpException
import java.io.IOException

class HeroesRepository(
    private val remoteDataSource: RemoteDataSource,
) {
    suspend fun getHeroesList(): List<Hero> {
        try {
            return remoteDataSource.getHeroesList()
        } catch (ex: Exception) {
            throw handleError(ex)
        }
    }

    suspend fun findById(id: Int): Hero {
        try {
            return remoteDataSource.getHeroById(id)
        } catch (ex: Exception) {
            throw handleError(ex)
        }
    }

    private fun handleError(throwable: Throwable): Throwable {
        val ex = when (throwable) {
            is HttpException ->
                when (throwable.code()) {
                    in 500..599 ->
                        ServerError(
                            throwable.code(),
                            throwable.message()
                        )
                    401 -> AuthorizationError(throwable)
                    else -> Fatal(throwable)
                }
            is IOException -> NetworkError(throwable)
            else -> NetworkError(throwable)
        }
        throw ex
    }
}