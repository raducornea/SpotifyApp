package com.spotifyapp.SpotifyProject.business.assemblers

import com.spotifyapp.SpotifyProject.data.entities.Artist
import com.spotifyapp.SpotifyProject.data.models.ArtistModel
import com.spotifyapp.SpotifyProject.presentation.controllers.ArtistController
import org.springframework.beans.BeanUtils

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component


/**
 * This class extends RepresentationModelAssemblerSupport which is required for Pagination.
 * It converts the Song Entity to the Song Model and has the code for it
 */
@Component
class ArtistModelAssembler : RepresentationModelAssemblerSupport<Artist, ArtistModel>(
    ArtistController::class.java,
    ArtistModel::class.java
) {

    override fun toModel(entity: Artist): ArtistModel {
        val model = ArtistModel()
        BeanUtils.copyProperties(entity, model)
        return model
    }

}