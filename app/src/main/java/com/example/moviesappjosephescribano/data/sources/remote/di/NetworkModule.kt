package com.example.moviesappjosephescribano.data.sources.remote.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moviesappjosephescribano.data.sources.remote.MoviesService
import com.example.moviesappjosephescribano.utils.Constantes.BASE_URL
import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideServiceInterceptor(): ServiceInterceptor = ServiceInterceptor()


    @Singleton
    @Provides
    fun provideHttpClient(serviceInterceptor: ServiceInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(serviceInterceptor)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun getGson(): Gson {
        return GsonBuilder().registerTypeAdapter(
            LocalDate::class.java,
            JsonDeserializer { jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext? ->
                LocalDate.parse(
                    jsonElement.asJsonPrimitive.asString
                )
            } as JsonDeserializer<LocalDate>)
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonSerializer { localDate: LocalDate, type: Type?, jsonSerializationContext: JsonSerializationContext? ->
                    JsonPrimitive(
                        localDate.toString()
                    )
                } as JsonSerializer<LocalDate>).create()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .build()
    }

    @Singleton
    @Provides
    fun movieService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)
}