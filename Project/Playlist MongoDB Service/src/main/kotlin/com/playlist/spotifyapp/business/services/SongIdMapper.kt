package com.playlist.spotifyapp.business.services

import com.google.gson.JsonParser
import com.playlist.spotifyapp.data.dao.Song
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SongIdMapper {

    @Autowired
    lateinit var urlCaller: UrlCaller

    fun getListOfSongsFromListOfIds(listOfSongIds: List<Int>): MutableList<Song>{
        val listOfSongs = mutableListOf<Song>()

        // pentru fiecare melodie din playlist se va edita afisarea
        listOfSongIds.forEach {
            // check if song/album service is available
            val url = "http://localhost:8080/api/songcollection/songs/${it}"
            val responseBody = urlCaller.getResponseBody(url)
            val resultString = responseBody.toString()

            val jsonParser = JsonParser()
            val jsonObject = jsonParser.parse(resultString).asJsonObject

            // extract properties we need from laboratory
            val id = jsonObject.get("id").asInt
            val name = jsonObject.get("name").asString
            val _links = jsonObject.get("_links").asJsonObject
            val selfLink = _links.get("self").asJsonObject.get("href").asString

            val song = Song(id, name, selfLink)
            listOfSongs.add(song)
        }

        return listOfSongs
    }
}