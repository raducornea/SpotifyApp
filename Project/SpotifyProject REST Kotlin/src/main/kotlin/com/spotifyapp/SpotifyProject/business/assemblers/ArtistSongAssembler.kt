package com.spotifyapp.SpotifyProject.business.assemblers

import com.spotifyapp.SpotifyProject.data.entities.ArtistSong
import com.spotifyapp.SpotifyProject.presentation.controllers.ArtistController
import com.spotifyapp.SpotifyProject.presentation.controllers.SongController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Component

@Component
class ArtistSongAssembler : RepresentationModelAssembler<ArtistSong, EntityModel<ArtistSong>> {
    override fun toModel(artistSong: ArtistSong): EntityModel<ArtistSong> {
        return EntityModel.of(
            artistSong,
            linkTo(WebMvcLinkBuilder.methodOn(ArtistController::class.java).one(artistSong.getPrimaryKey().getUuid())).withSelfRel(),
            linkTo(WebMvcLinkBuilder.methodOn(SongController::class.java).one(artistSong.getPrimaryKey().getId())).withSelfRel(),
        )
    }
}