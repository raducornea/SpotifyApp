package com.gateway.spotifyapp.business.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Service
class UrlCaller {

    @Autowired
    private lateinit var jsonOperations: JSONOperations

    private fun getResponseBody(url: String): Any {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == 500){
            return "Resource on $url not available."
        }

        try {
            val inputStream = connection.inputStream
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            val response = StringBuilder()
            var line = bufferedReader.readLine()
            while (line != null) {
                response.append(line)
                line = bufferedReader.readLine()
            }

            bufferedReader.close()
            inputStreamReader.close()
            inputStream.close()

            return response.toString()

        } catch(_: Exception) {
            return "Service on $url not available."
        }
    }

    fun getResponse(url: String): Any {

        val responseBody = getResponseBody(url)
        val resultString = responseBody.toString()
        return jsonOperations.prettyJSONString(resultString)
    }
}