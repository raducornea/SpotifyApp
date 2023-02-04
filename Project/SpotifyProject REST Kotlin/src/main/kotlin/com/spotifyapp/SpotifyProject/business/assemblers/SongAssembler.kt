package com.spotifyapp.SpotifyProject.business.assemblers

import com.spotifyapp.SpotifyProject.data.entities.Song
import com.spotifyapp.SpotifyProject.presentation.controllers.SongController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Component

@Component
class SongAssembler : RepresentationModelAssembler<Song, EntityModel<Song>> {
    override fun toModel(song: Song): EntityModel<Song> {
        return EntityModel.of(
            song,
            linkTo(WebMvcLinkBuilder.methodOn(SongController::class.java).one(song.getId())).withSelfRel(),
            linkTo(WebMvcLinkBuilder.methodOn(SongController::class.java).all()).withRel("songs")
        )
    }
}