package com.bps_e

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.junit.Assert.*
import org.junit.Test

class HttpClientTest {
    @Serializable
    data class Name (
        @SerialName("name") val name: String,
        @SerialName("url") val url: String,
    )

    @Serializable
    data class ResourceList (
        @SerialName("count") val count: Int,
        @SerialName("next") val next: String?,
        @SerialName("previous") val previous: String?,
        @SerialName("results") val results: List<Name>,
    )

    @Test
    fun getTest() {
        val url = "https://pokeapi.co/api/v2/pokemon?limit=1"

        runBlocking {
            HttpClient.Get(url, onError = {
                println(it.toString())
                assert(false)
            }) { code, data ->
                assertEquals(code, HttpClient.OK)
                println("$code: ${String(data, Charsets.UTF_8)}")
            }
        }
    }

    @Test
    fun getErrorTest() {
        val url = "https://x.x"

        runBlocking {
            HttpClient.Get(url, onError = {
                println(it.toString())
            }) { _, _ ->
                assert(false)
            }
        }
    }

    @Test
    fun apiTest() {
        val url = "https://pokeapi.co/api/v2/pokemon?limit=2"

        runBlocking {
            HttpClient.Api<ResourceList>(url, onError = {
                println(it.toString())
                assert(false)
            }) { code, data ->
                assertEquals(code, HttpClient.OK)
                println("$code: ${data.next}")

                val offset = HttpClient.parseUrlParams(data.next!!)
                assert(!offset["offset"].isNullOrEmpty())
                println("${offset["offset"]}")
            }
        }
    }
}