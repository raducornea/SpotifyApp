package com.playlist.spotifyapp.data.dao

import org.bson.types.ObjectId

data class PlaylistMapper(val id: ObjectId, val name: String, val songs: List<Song>, val uuid: String)