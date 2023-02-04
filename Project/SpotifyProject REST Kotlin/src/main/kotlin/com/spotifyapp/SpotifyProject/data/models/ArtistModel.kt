package com.spotifyapp.SpotifyProject.data.models

import com.spotifyapp.SpotifyProject.data.entities.Genre
import com.spotifyapp.SpotifyProject.data.entities.Type
import org.springframework.hateoas.RepresentationModel

class ArtistModel : RepresentationModel<ArtistModel>() {
    private var _uuid: String = ""
    private var name: String? = null
    private var active: Boolean? = null

    fun getUuid(): String = _uuid
    fun getName(): String? = name
    fun isActive(): Boolean? = active

    fun setUuid(uuid: String){
        this._uuid = uuid
    }
    fun setName(name: String?){
        this.name = name
    }
    fun setActivity(active: Boolean?){
        this.active = active
    }
}
