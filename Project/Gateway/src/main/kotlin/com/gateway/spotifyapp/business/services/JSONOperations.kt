package com.gateway.spotifyapp.business.services

import com.google.gson.*
import org.springframework.stereotype.Service
import java.util.*


@Service
class JSONOperations {

    fun prettyJSONString(stringToTransform: String): String{

        // code might crash, so it's better to return something rather than letting it crash
        try {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val jsonElement: JsonElement = JsonParser.parseString(stringToTransform)
            val prettyJsonString: String = gson.toJson(jsonElement)
            return prettyJsonString

        } catch (exception: Exception) {
            return stringToTransform
        }
    }

    fun geetRolesFromJSONString(stringToTransform: String): List<Int> {

        // code might crash, so it's better to return something rather than letting it crash
        try {
            val gson: Gson = GsonBuilder().setPrettyPrinting().create()
            val jsonObject: JsonObject = gson.fromJson(stringToTransform, JsonObject::class.java)
            val jsonElement: JsonElement = jsonObject.getAsJsonArray("roles")
            val rolesArray: Array<Int> = gson.fromJson(jsonElement, Array<Int>::class.java)
            val rolesList: List<Int> = rolesArray.asList()
            return rolesList

        } catch (exception: Exception) {
            return listOf()
        }
    }
}