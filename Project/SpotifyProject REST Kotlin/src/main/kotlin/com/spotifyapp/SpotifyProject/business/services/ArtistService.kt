package com.spotifyapp.SpotifyProject.business.services

import com.spotifyapp.SpotifyProject.business.interfaces.IArtistService
import com.spotifyapp.SpotifyProject.data.entities.Artist
import com.spotifyapp.SpotifyProject.persistence.repositories.ArtistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class ArtistService : IArtistService {
    @Autowired
    private lateinit var artistRepository: ArtistRepository

    fun getFilteredArtists(
        pageable: Pageable,
        name: Optional<String>, match: Optional<String>,
    ): Page<Artist> {
        if (match.isPresent && match.get() == "exact") {
            return artistRepository.findAllByFiltersExact(pageable, name.get())
        }
        else{
            return artistRepository.findAllByFiltersPartial(pageable, name.get())
        }
    }
}