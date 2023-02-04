/**
 * Controllerele au business logic
 *
 * @RestController - datele returnate de metode ajung direct in raspunsul body-ului in loc sa randeze template-ul
 * to wrap repository with web layer, we use Spring MVC (easy -> RestController)
 * not much to code, but now we can focus more on infrastructure
 * EmployeeRepository e injectat in constructor de EmployeeController
 *
 * @RequestBody mapeaza HttpRequest body incat sa TRANSFERE un DO (Domain Object) (DTO?) (automatic serialization)
 * @ResponseBody means the returned String is the response, not a view name
 * @RequestParam means it is a parameter from the GET or POST request
 */
package com.spotifyapp.SpotifyProject.presentation.controllers

import com.spotifyapp.SpotifyProject.business.assemblers.ArtistAssembler
import com.spotifyapp.SpotifyProject.business.assemblers.ArtistSongAssembler
import com.spotifyapp.SpotifyProject.business.assemblers.SongAssembler
import com.spotifyapp.SpotifyProject.data.entities.Artist
import com.spotifyapp.SpotifyProject.data.entities.ArtistSong
import com.spotifyapp.SpotifyProject.data.entities.Song
import com.spotifyapp.SpotifyProject.persistence.repositories.ArtistRepository
import com.spotifyapp.SpotifyProject.persistence.repositories.ArtistSongRepository
import com.spotifyapp.SpotifyProject.persistence.repositories.SongRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors


/** asa ii dai wrap, la propriu doar l-ai pus intr-o clasa */
@RestController
@CrossOrigin
class ArtistSongController() {
    @Autowired
    private lateinit var artistSongRepository: ArtistSongRepository
    @Autowired
    private lateinit var artistSongAssembler: ArtistSongAssembler

    @Autowired
    private lateinit var songRepository: SongRepository
    @Autowired
    private lateinit var songAssembler: SongAssembler

    @Autowired
    private lateinit var artistRepository: ArtistRepository
    @Autowired
    private lateinit var artistAssembler: ArtistAssembler

    /** obtin toate melodiile unui artist */
    @GetMapping("/api/songcollection/artists/{uuid}/songs")
    fun getSongsByArtistId(@PathVariable uuid: String): CollectionModel<EntityModel<Song>> {
        // gasim artistul si melodiile lui
        val artistSongs = artistSongRepository.findAllSongsByUuid(uuid).stream()
            .map { artistSong: ArtistSong ->
                val id = artistSong.getPrimaryKey().getId()
                val song = songRepository.findById(id)
                    .orElseThrow { Exception(id.toString()) }
                songAssembler.toModel(song)
//                val artistSongCompositePrimaryKey = ArtistSongCompositePrimaryKey(uuid, id)
//                val artistSong = ArtistSong(artistSongCompositePrimaryKey)
//                artistSongAssembler.toModel(artistSong)
            }
            .collect(Collectors.toList())

        return CollectionModel.of(artistSongs, linkTo(methodOn(SongController::class.java).all()).withSelfRel())
    }

    /** obtin toti artistii unei melodii */
    @GetMapping("/api/songcollection/songs/{id}/artists")
    fun getArtistsBySongId(@PathVariable id: Int): CollectionModel<EntityModel<Artist>> {
        // gasim artistul si melodiile lui
        val songArtists = artistSongRepository.findAllArtistsById(id).stream()
            .map { artistSong: ArtistSong ->
                val uuid = artistSong.getPrimaryKey().getUuid()
                val artist = artistRepository.findById(uuid)
                    .orElseThrow { Exception(uuid) }
                artistAssembler.toModel(artist)
            }
            .collect(Collectors.toList())

        return CollectionModel.of(songArtists, linkTo(methodOn(SongController::class.java).all()).withSelfRel())
    }

    // todo
    //  1 restul operatiilor CUD? YES
    //  + chestia de Throw la update?

    // todo
    //  - documentul descriptiv al serviciilor restful?
    //  - mai concret, ce anume ar trebui sa facem?
}