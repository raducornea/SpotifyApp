package com.playlist.spotifyapp.data.entities

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "playlists")
class Playlist() {
    @Id
    private var id: ObjectId = ObjectId()
    private var name: String = ""
    private var songs: MutableList<Int> = mutableListOf()
    private var uuid: String = ""

    constructor(name: String, songs: MutableList<Int>, uuid: String) : this(){
        this.name = name
        this.songs = songs
        this.uuid = uuid
    }

    fun getId(): ObjectId = id
    fun getName(): String = name
    fun getSongs(): MutableList<Int> = songs
    fun getUuid(): String = uuid

    fun setId(id: ObjectId){
        this.id = id
    }
    fun setName(name: String){
        this.name = name
    }
    fun setSongs(songs: MutableList<Int>){
        this.songs = songs
    }
    fun setUuid(uuid: String){
        this.uuid = uuid
    }
}
