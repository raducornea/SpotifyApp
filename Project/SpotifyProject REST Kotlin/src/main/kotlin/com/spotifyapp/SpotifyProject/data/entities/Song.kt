/**
 * create table songs_albums( id int(6) auto_increment, name varchar(100) not null, genre enum('Chill', 'Jazz', 'Rock', 'Electronica', 'Pop', 'Rap', 'Unknown'), release_year year, type enum('album', 'song', 'single'), parent_id int(6), primary key(id));
 */

package com.spotifyapp.SpotifyProject.data.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "songs_albums")
class Song() {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private var _id: Int = 0
    private var name: String = ""
    @Enumerated(EnumType.STRING)
    private var genre: Genre = Genre.Unknown
    @Column(name = "release_year")
    private var releaseYear: Int? = null
    @Enumerated(EnumType.STRING)
    private var type: Type = Type.song
    @Column(name = "parent_id")
    private var parentId: Int? = null

    constructor(name: String, genre: Genre, releaseYear: Int, type: Type, parentId: Int) : this(){
        this.name = name
        this.genre = genre
        this.releaseYear = releaseYear
        this.type = type
        this.parentId = parentId
    }

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Song) return false
        return (Objects.equals(_id, other._id) && Objects.equals(name, other.name)
                && Objects.equals(genre, other.genre) && Objects.equals(releaseYear, other.releaseYear)
                && Objects.equals(type, other.type) && Objects.equals(parentId, other.parentId))
    }

    override fun hashCode(): Int {
        return Objects.hash(_id, name, genre, releaseYear, type, parentId)
    }

    override fun toString(): String {
        return "Song{id=$_id, name='$name', genre='$genre', release_year='$releaseYear', type='$type', parentId='$parentId'}"
    }
}