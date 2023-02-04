package com.playlist.spotifyapp.persistence.repositories

import com.playlist.spotifyapp.data.entities.Playlist
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface PlaylistRepository : MongoRepository<Playlist, String> {

    @Query("{ 'name' : ?0 }")
    fun findPlaylistByName(name: String): Playlist
}