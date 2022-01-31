package com.angeldearbo.marvelheroes.data.repository.exceptions

sealed class HeroesError : Throwable()

sealed class RetryError : HeroesError()
data class NetworkError(val t: Throwable) : RetryError()
data class ServerError(val code: Int, val msg: String?) : RetryError()

sealed class FatalError : HeroesError()
data class Fatal(val t: Throwable) : FatalError()
data class AuthorizationError(val t: Throwable) : FatalError()
