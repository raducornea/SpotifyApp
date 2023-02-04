package com.spotifyapp.SpotifyProject.data.models

import com.spotifyapp.SpotifyProject.data.entities.Genre
import com.spotifyapp.SpotifyProject.data.entities.Type
import org.springframework.hateoas.RepresentationModel

class SongModel : RepresentationModel<SongModel>() {
    private var _id: Int = 0
    private var name: String = ""
    private var genre: Genre = Genre.Unknown
    private var releaseYear: Int? = null
    private var type: Type = Type.song
    private var parentId: Int? = null

    fun getId(): Int = _id
    fun getName(): String = name
    fun getGenre(): Genre = genre
    fun getReleaseYear(): Int? = releaseYear
    fun getType(): Type = type
    fun getParentId(): Int? = parentId

    fun setId(id: Int){
        this._id = id
    }
    fun setName(name: String){
        this.name = name
    }
    fun setGenre(genre: Genre){
        this.genre = genre
    }
    fun setReleaseYear(release: Int?){
        this.releaseYear = release
    }
    fun setType(type: Type){
        this.type = type
    }
    fun setParentId(parentId: Int?){
        this.parentId = parentId
    }
}
