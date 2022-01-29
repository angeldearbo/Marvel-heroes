package com.angeldearbo.marvelheroes.framework

import com.angeldearbo.marvelheroes.BuildConfig
import com.angeldearbo.marvelheroes.data.datasources.RemoteDataSource
import com.angeldearbo.marvelheroes.framework.data.datasources.HeroesRemoteDataSource
import com.angeldearbo.marvelheroes.framework.data.datasources.MarvelService
import com.angeldearbo.marvelheroes.framework.ui.common.md5
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider(): String = BuildConfig.MARVEL_BASE_URL

    @Provides
    @Singleton
    fun httpClientProvider(): OkHttpClient {
        val authInterceptor = { chain: Interceptor.Chain ->
            val ts = System.currentTimeMillis()

            val hash = "$ts${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_PUBLIC_KEY}".md5()
            val request = chain.request()
            val url = request.url
                .newBuilder()
                .addQueryParameter("ts", ts.toString())
                .addQueryParameter("apikey", BuildConfig.MARVEL_PUBLIC_KEY)
                .addQueryParameter("hash", hash)
                .build()
            val updated = request.newBuilder()
                .url(url)
                .build()

            chain.proceed(updated)
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) })
                }
            }
            .build()
    }

    @Provides
    @Singleton
    fun retrofitProvider(
        @Named("baseUrl") baseUrl: String,
        httpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    @Provides
    @Singleton
    fun marvelServiceProvider(retrofit: Retrofit): MarvelService =
        retrofit.create(MarvelService::class.java)

    @Provides
    fun remoteDataSourceProvider(marvelService: MarvelService): RemoteDataSource {
        return HeroesRemoteDataSource(marvelService)
    }

}