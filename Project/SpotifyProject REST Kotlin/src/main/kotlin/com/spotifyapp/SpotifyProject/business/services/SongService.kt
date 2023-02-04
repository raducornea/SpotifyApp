package com.spotifyapp.SpotifyProject.business.services

import com.spotifyapp.SpotifyProject.business.interfaces.ISongService
import com.spotifyapp.SpotifyProject.data.entities.Song
import com.spotifyapp.SpotifyProject.persistence.repositories.SongRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class SongService : ISongService {
    @Autowired
    private lateinit var songRepository: SongRepository

    override fun getAllSongsAndAlbums(): List<Song> {
        return songRepository.findAll()
    }

    override fun getFilteredSongsAndAlbums(
        pageable: Pageable,
        title: Optional<String>,
        genre: Optional<String>,
        releaseYear: Optional<Int>,
        match: Optional<String>
    ): Page<Song> {
        if (match.isPresent && match.get() == "exact") {
            return songRepository.findAllByFiltersExact(pageable, title.get(), genre.get(), releaseYear.get())
        }
        else{
            return songRepository.findAllByFiltersPartial(pageable, title.get(), genre.get(), releaseYear.get())
        }
    }
}