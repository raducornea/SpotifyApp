package com.playlist.spotifyapp.business.services

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Service
class UrlCaller {
    fun getResponseBody(url: String): Any {
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
}