package com.spotifyapp.SpotifyProject.data.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "artists")
class Artist() {
    @Id
    @Column(name = "uuid", nullable = false, unique = true)
    private var _uuid: String = ""
    private var name: String? = null
    private var active: Boolean? = null

    constructor(uuid: String, name: String?, active: Boolean) : this(){
        this._uuid = uuid
        this.name = name
        this.active = active
    }

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Artist) return false
        return (Objects.equals(_uuid, other._uuid) && Objects.equals(name, other.name)
                && Objects.equals(active, other.active))
    }

    override fun hashCode(): Int {
        return Objects.hash(_uuid, name, active)
    }

    override fun toString(): String {
        return "Artist{uuid=$_uuid, name='$name', active='$active'}"
    }
}