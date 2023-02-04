package com.spotifyapp.SpotifyProject.business.assemblers

import com.spotifyapp.SpotifyProject.data.entities.Song
import com.spotifyapp.SpotifyProject.data.models.SongModel
import com.spotifyapp.SpotifyProject.presentation.controllers.SongController
import org.springframework.beans.BeanUtils

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component


/**
 * This class extends RepresentationModelAssemblerSupport which is required for Pagination.
 * It converts the Song Entity to the Song Model and has the code for it
 */
@Component
class SongModelAssembler : RepresentationModelAssemblerSupport<Song, SongModel>(
    SongController::class.java,
    SongModel::class.java
) {

    override fun toModel(entity: Song): SongModel {
        val model = SongModel()
        BeanUtils.copyProperties(entity, model)
        return model
    }
}