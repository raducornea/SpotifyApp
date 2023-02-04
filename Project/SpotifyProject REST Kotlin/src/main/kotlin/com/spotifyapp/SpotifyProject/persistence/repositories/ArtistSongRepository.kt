package com.spotifyapp.SpotifyProject.persistence.repositories

import com.spotifyapp.SpotifyProject.data.entities.ArtistSong
import com.spotifyapp.SpotifyProject.data.entities.ArtistSongCompositePrimaryKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ArtistSongRepository : JpaRepository<ArtistSong, ArtistSongCompositePrimaryKey> {
    @Query(value = "select * from artists_songs_albums where uuid=:uuid", nativeQuery = true)
    fun findAllSongsByUuid(
        @Param("uuid")
        uuid: String,
    ): Collection<ArtistSong>

    @Query(value = "select * from artists_songs_albums where id=:id", nativeQuery = true)
    fun findAllArtistsById(
        @Param("id")
        id: Int
    ): Collection<ArtistSong>
}