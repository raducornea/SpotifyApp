package com.spotifyapp.SpotifyProject.persistence.repositories

import com.spotifyapp.SpotifyProject.data.entities.Song
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SongRepository : JpaRepository<Song, Int> {
    @Query(value = "select * from songs_albums where name=:title", nativeQuery = true)
    fun findAllByExactTitle(
        @Param("title")
        title: String,
    ): Collection<Song>

    @Query(value = "select * from songs_albums where name like %:title%", nativeQuery = true)
    fun findAllByPartialTitle(
        @Param("title")
        title: String,
    ): Collection<Song>

    @Query(value = "select * from songs_albums where genre=:genre", nativeQuery = true)
    fun findAllByGenre(
        @Param("genre")
        genre: String,
    ): Collection<Song>

    @Query(value = "select * from songs_albums where release_year=:releaseYear", nativeQuery = true)
    fun findAllByReleaseYear(
        @Param("releaseYear")
        releaseYear: Int,
    ): Collection<Song>

    @Query(
        value = "select * from songs_albums " +
                "where (name=:title or :title='') " +
                "and (genre=:genre or :genre='') " +
                "and (release_year=:releaseYear or :releaseYear=-1)",
        nativeQuery = true
    )
    fun findAllByFiltersExact(
        pageable: Pageable,
        @Param("title")
        title: String,
        @Param("genre")
        genre: String,
        @Param("releaseYear")
        releaseYear: Int,
    ): Page<Song>

    @Query(
        value = "select * from songs_albums " +
                "where (name like %:title%) " +
                "and (genre=:genre or :genre='') " +
                "and (release_year=:releaseYear or :releaseYear=-1)",
        nativeQuery = true
    )
    fun findAllByFiltersPartial(
        pageable: Pageable,
        @Param("title")
        title: String,
        @Param("genre")
        genre: String,
        @Param("releaseYear")
        releaseYear: Int,
    ): Page<Song>
}