package com.playlist.spotifyapp.presentation.controllers

import com.google.gson.JsonParser
import com.playlist.spotifyapp.business.services.SongIdMapper
import com.playlist.spotifyapp.business.services.UrlCaller
import com.playlist.spotifyapp.data.entities.Playlist
import com.playlist.spotifyapp.data.dao.PlaylistMapper
import com.playlist.spotifyapp.persistence.repositories.PlaylistRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


//todo: ar trebui sa existe logica si pentru: delete song from every playlist upon removing it
@RestController
class PlaylistController {

    @Autowired
    lateinit var urlCaller: UrlCaller

    @Autowired
    lateinit var songIdMapper: SongIdMapper

    @Autowired
    lateinit var playlistRepository: PlaylistRepository

    @GetMapping("/playlists/{name}")
    fun one(@PathVariable name: String): Any {
        try {
            val playlist = playlistRepository.findPlaylistByName(name)
            val listOfSongIds = playlist.getSongs()

            // ne construim o lista noua de melodii
            val listOfSongs = songIdMapper.getListOfSongsFromListOfIds(listOfSongIds)

            // returnam lista de melodii reformatata intocmai cum s-a vrut in laborator
            return PlaylistMapper(playlist.getId(), playlist.getName(), listOfSongs, playlist.getUuid())

        } catch (exception: Exception) {
            return ResponseEntity.badRequest().body("Nu exista playlist cu titlul respectiv.")
        }
    }

    @GetMapping("/playlists")
    fun all(): Any {
        val playlists = playlistRepository.findAll()

        val mappedPlaylist = mutableListOf<PlaylistMapper>()
        playlists.forEach {playlist ->
            val listOfSongIds = playlist.getSongs()

            // ne construim o lista noua de melodii
            val listOfSongs = songIdMapper.getListOfSongsFromListOfIds(listOfSongIds)

            // returnam lista de melodii reformatata intocmai cum s-a vrut in laborator
            mappedPlaylist.add(PlaylistMapper(playlist.getId(), playlist.getName(), listOfSongs, playlist.getUuid()))
        }

        return mappedPlaylist
    }

    @PostMapping("/playlists")
    fun newPlaylist(@RequestBody newPlaylist: Playlist): Any {
        val id = ObjectId.get()
        newPlaylist.setId(id)

        val possiblePlaylistCopy = playlistRepository.findPlaylistByName(newPlaylist.getName())
        if (possiblePlaylistCopy.getUuid() != newPlaylist.getUuid()){
            playlistRepository.save(newPlaylist)
            return ResponseEntity.ok().body("Playlist Created!")
        }

        return ResponseEntity.badRequest().body("Duplicate playlist!")
    }

    @PutMapping("/playlists/{playlistId}/{songId}")
    fun addPlaylistSong(@PathVariable playlistId: String, @PathVariable songId: Int): Any {
        try {
            val playlist = playlistRepository.findById(playlistId).get()

            // check if song/album service is available
            val url = "http://localhost:8080/api/songcollection/songs/${songId}"
            val responseBody = urlCaller.getResponseBody(url)
            val resultString = responseBody.toString()

            try {
                val jsonParser = JsonParser()
                val jsonObject = jsonParser.parse(resultString).asJsonObject
                val id = jsonObject.get("id").asInt
                val name = jsonObject.get("name").asString
                val _links = jsonObject.get("_links").asJsonObject
                val selfLink = _links.get("self").asJsonObject.get("href")

                val songsList = playlist.getSongs()
                if (!songsList.contains(songId)){
                    songsList.add(songId)
                }
                playlist.setSongs(songsList)

                return playlistRepository.save(playlist)

            } catch (exception: Exception) {
                return ResponseEntity.badRequest().body(responseBody)
            }

        } catch (exception: Exception) {
            return ResponseEntity.badRequest().body("Nu exista playlist-ul respectiv.")
        }
    }

    @DeleteMapping("/playlists/{playlistId}/{songId}")
    fun deletePlaylistSong(@PathVariable playlistId: String, @PathVariable songId: Int): Any {
        try {
            val playlist = playlistRepository.findById(playlistId).get()

            // check if song/album service is available
            val url = "http://localhost:8080/api/songcollection/songs/${songId}"
            val responseBody = urlCaller.getResponseBody(url)
            val resultString = responseBody.toString()

            try {
                val jsonParser = JsonParser()
                val jsonObject = jsonParser.parse(resultString).asJsonObject
                val id = jsonObject.get("id").asInt
                val name = jsonObject.get("name").asString
                val _links = jsonObject.get("_links").asJsonObject
                val selfLink = _links.get("self").asJsonObject.get("href")

                val songsList = playlist.getSongs()
                if (songsList.contains(songId)){
                    songsList.remove(songId)
                }
                playlist.setSongs(songsList)

                return playlistRepository.save(playlist)

            } catch (exception: Exception) {
                return ResponseEntity.badRequest().body(responseBody)
            }

        } catch (exception: Exception) {
            return ResponseEntity.badRequest().body("Nu exista playlist-ul respectiv.")
        }
    }

    @DeleteMapping("/playlists/{id}")
    fun removePlaylist(@PathVariable id: String) {
        playlistRepository.deleteById(id)
    }

}