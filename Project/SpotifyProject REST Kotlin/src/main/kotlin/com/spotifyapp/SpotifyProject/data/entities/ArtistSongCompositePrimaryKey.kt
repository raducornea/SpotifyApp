package com.spotifyapp.SpotifyProject.data.entities

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

// primary key mixt
@Embeddable
class ArtistSongCompositePrimaryKey : Serializable {
    @Column(name = "uuid", nullable = false)
    private var uuid: String = ""

    @Column(name = "id", nullable = false)
    private var id: Int = 0

    constructor(uuid: String, id: Int) : this(){
        this.uuid = uuid
        this.id = id
    }

    constructor()

    fun getUuid(): String = uuid
    fun getId(): Int = id

    fun setUuid(uuid: String){
        this.uuid = uuid
    }
    fun setId(id: Int){
        this.id = id
    }
}