package com.spotifyapp.SpotifyProject.data.entities

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "artists_songs_albums")
class ArtistSong() {
    @EmbeddedId
    private lateinit var primaryKey: ArtistSongCompositePrimaryKey

    constructor(primaryKey: ArtistSongCompositePrimaryKey) : this(){
        this.primaryKey = primaryKey
    }

    fun getUuid(): String = primaryKey.getUuid()
    fun getId(): Int = primaryKey.getId()
    fun getPrimaryKey(): ArtistSongCompositePrimaryKey = primaryKey

    fun setPrimaryKey(primaryKey: ArtistSongCompositePrimaryKey){
        this.primaryKey = primaryKey
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ArtistSong) return false
        return (Objects.equals(primaryKey.getUuid(), other.primaryKey.getUuid()) &&
                Objects.equals(primaryKey.getId(), other.primaryKey.getId()))
    }

    override fun hashCode(): Int {
        return Objects.hash(primaryKey.getUuid(), primaryKey.getId())
    }

    override fun toString(): String {
        return "Artist{uuid=${primaryKey.getUuid()}, id='${primaryKey.getUuid()}'}"
    }
}


