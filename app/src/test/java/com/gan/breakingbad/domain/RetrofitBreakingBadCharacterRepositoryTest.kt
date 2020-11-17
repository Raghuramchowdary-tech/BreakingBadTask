package com.gan.breakingbad.domain

import com.gan.breakingbad.characters.data.BreakingBadCharacter
import com.gan.breakingbad.characters.data.BreakingBadCharacterResponse
import com.gan.breakingbad.characters.data.RetrofitBreakingBadRepository
import com.gan.breakingbad.utils.toJson
import junit.framework.Assert.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.nio.charset.Charset
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RetrofitBreakingBadCharacterRepositoryTest {

    private val mockServer = MockWebServer()

    @Before
    fun setUp() {
        mockServer.start()
    }

    @After
    fun shutdown() {
        mockServer.shutdown()
    }

    private val currentThreadExecutor =
        object : ExecutorService by Executors.newSingleThreadExecutor() {
            override fun execute(command: Runnable) {
                command.run()
            }
        }

    @Test
    fun canFetchBreakingBadResultsFromTheNetwork() {

        val expectedBreakingBadResponseResult = emptyList<BreakingBadCharacter>()

        mockServer.enqueue(
            MockResponse()
                .addHeader("Content-Type", "application/json")
                .setBody(toJson(expectedBreakingBadResponseResult))
        )

        val breakingBadRepository =
            RetrofitBreakingBadRepository(
                mockServer.url("/").toString(),
                currentThreadExecutor
            )

        lateinit var actualBreakingBadResponse: BreakingBadCharacterResponse
        breakingBadRepository.fetchBreakingBadCharacters {
            actualBreakingBadResponse = it
        }
        mockServer.takeRequest()

        assertEquals(
            BreakingBadCharacterResponse.Success(expectedBreakingBadResponseResult),
            actualBreakingBadResponse
        )
    }

    @Test
    fun willReportNetworkError() {

        mockServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))

        val breakingBadRepository =
            RetrofitBreakingBadRepository(
                mockServer.url("/").toString(),
                currentThreadExecutor
            )

        lateinit var actualBreakingBadResponse: BreakingBadCharacterResponse
        breakingBadRepository.fetchBreakingBadCharacters {
            actualBreakingBadResponse = it
        }

        assertEquals(BreakingBadCharacterResponse.NetworkError, actualBreakingBadResponse)
    }

    @Test
    fun willReportServerError() {

        mockServer.enqueue(
            MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(500)
                .setBody(
                    """
                    {
                        "timestamp": "2020-11-01T16:06:05.232+0000",
                        "path": "/api/characters",
                        "status": 500,
                        "error": "Internal Server Error",
                        "message": "404 Not Found from GET https://breakingbadapi.com/api/characters"
                    }
                """.trimIndent()
                )
        )

        val breakingBadRepository =
            RetrofitBreakingBadRepository(
                mockServer.url("/").toString(),
                currentThreadExecutor
            )

        lateinit var actualBreakBadCharacterResponse: BreakingBadCharacterResponse

        breakingBadRepository.fetchBreakingBadCharacters {
            actualBreakBadCharacterResponse = it
        }

        val request = mockServer.takeRequest()
        request.body.readString(Charset.defaultCharset())

        assertEquals(BreakingBadCharacterResponse.ServerError, actualBreakBadCharacterResponse)
    }

}

