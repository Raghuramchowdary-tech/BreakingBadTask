package com.gan.breakingbad.characters.data

import com.gan.breakingbad.characters.domain.BreakingBadRepository
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RetrofitBreakingBadRepository @JvmOverloads constructor(
    url: String,
    executorService: ExecutorService = Executors.newCachedThreadPool()
) :  BreakingBadRepository {

    interface BreakingBadServiceDao {
        @GET("/api/characters")
        fun fetchSearchResults(): Call<List<BreakingBadCharacter>>
    }

    private val breakingBadServiceDao: BreakingBadServiceDao

    init {
        val retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .dispatcher(Dispatcher(executorService))
                    .build()
            )
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()
                )
            )
            .baseUrl(url)
            .build()

        breakingBadServiceDao = retrofit.create(BreakingBadServiceDao::class.java)
    }

    override fun fetchBreakingBadCharacters(
        callback: (BreakingBadCharacterResponse) -> Unit
    ) {
        breakingBadServiceDao.fetchSearchResults().enqueue(object :
            Callback<List<BreakingBadCharacter>?> {
            override fun onFailure(call: Call<List<BreakingBadCharacter>?>, error: Throwable) {
                callback(
                    when (error) {
                        is JsonDataException -> BreakingBadCharacterResponse.ServerError
                        else -> BreakingBadCharacterResponse.NetworkError
                    }
                )
            }

            override fun onResponse(
                call: Call<List<BreakingBadCharacter>?>,
                response: Response<List<BreakingBadCharacter>?>
            ) {
                callback(
                    when {
                        response.isSuccessful -> BreakingBadCharacterResponse.Success(
                            response.body()!!
                        )
                        else -> BreakingBadCharacterResponse.ServerError
                    }
                )
            }
        })
    }


}