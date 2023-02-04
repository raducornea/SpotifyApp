package com.spotifyapp.SpotifyProject.business.interfaces

import com.spotifyapp.SpotifyProject.data.entities.Song
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface ISongService {
    fun getAllSongsAndAlbums(): List<Song>

    fun getFilteredSongsAndAlbums(
        pageable: Pageable,
        title: Optional<String>,
        genre: Optional<String>,
        releaseYear: Optional<Int>,
        match: Optional<String>
    ) : Page<Song>
}