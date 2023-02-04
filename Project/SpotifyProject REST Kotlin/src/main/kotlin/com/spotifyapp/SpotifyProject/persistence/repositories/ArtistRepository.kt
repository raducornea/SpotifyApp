package com.spotifyapp.SpotifyProject.persistence.repositories

import com.spotifyapp.SpotifyProject.data.entities.Artist
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

//interface ArtistRepository : CrudRepository<Artist, String> {
//    fun findByName(name: String): Artist
//}

interface ArtistRepository : JpaRepository<Artist, String> {
    @Query(value = "select * from artists where name=:name", nativeQuery = true)
    fun findAllByExactName(
        @Param("name")
        name: String,
    ): Collection<Artist>

    @Query(value = "select * from artists where name like %:name%", nativeQuery = true)
    fun findAllByPartialName(
        @Param("name")
        name: String,
    ): Collection<Artist>

    @Query(
        value = "select * from artists " +
                "where (name=:title or :name='')",
        nativeQuery = true
    )
    fun findAllByFiltersExact(
        pageable: Pageable,
        @Param("name")
        name: String
    ): Page<Artist>

    @Query(
        value = "select * from artists " +
                "where (name like %:name%)",
        nativeQuery = true
    )
    fun findAllByFiltersPartial(
        pageable: Pageable,
        @Param("name")
        name: String
    ): Page<Artist>
}