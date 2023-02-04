package com.spotifyapp.SpotifyProject.business.assemblers

import com.spotifyapp.SpotifyProject.data.entities.Artist
import com.spotifyapp.SpotifyProject.presentation.controllers.ArtistController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Component

@Component
class ArtistAssembler : RepresentationModelAssembler<Artist, EntityModel<Artist>> {
    override fun toModel(artist: Artist): EntityModel<Artist> {
        return EntityModel.of(
            artist,
            linkTo(WebMvcLinkBuilder.methodOn(ArtistController::class.java).one(artist.getUuid())).withSelfRel(),
            linkTo(WebMvcLinkBuilder.methodOn(ArtistController::class.java).all()).withRel("artists")
        )
    }
}