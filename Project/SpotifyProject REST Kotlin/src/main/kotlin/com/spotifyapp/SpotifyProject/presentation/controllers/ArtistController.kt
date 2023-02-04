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
import com.spotifyapp.SpotifyProject.business.assemblers.ArtistModelAssembler
import com.spotifyapp.SpotifyProject.business.assemblers.SongModelAssembler
import com.spotifyapp.SpotifyProject.business.services.ArtistService
import com.spotifyapp.SpotifyProject.data.entities.Artist
import com.spotifyapp.SpotifyProject.data.entities.Song
import com.spotifyapp.SpotifyProject.data.models.ArtistModel
import com.spotifyapp.SpotifyProject.data.models.SongModel
import com.spotifyapp.SpotifyProject.persistence.repositories.ArtistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors


/** asa ii dai wrap, la propriu doar l-ai pus intr-o clasa */
@RestController
@CrossOrigin
class ArtistController() {
    @Autowired
    private lateinit var artistRepository: ArtistRepository

    @Autowired
    private lateinit var artistAssembler: ArtistAssembler

    @Autowired
    private lateinit var artistService: ArtistService

    /** TUTORIAL FOLOSIT:
     * https://www.springcloud.io/post/2022-04/hateoas-spring-boot-and-jpa/#gsc.tab=0 */
    @Autowired
    private lateinit var pagedResourcesAssembler: PagedResourcesAssembler<Artist>

    @Autowired
    private lateinit var artistModelAssembler: ArtistModelAssembler

    /** obtin toti artistii */
    @GetMapping("/api/songcollection/artists")
    fun all(): CollectionModel<EntityModel<Artist>> {
        val artists = artistRepository.findAll().stream()
            .map { artist: Artist -> artistAssembler.toModel(artist) }
            .collect(Collectors.toList())
        return CollectionModel.of(artists, linkTo(methodOn(ArtistController::class.java).all()).withSelfRel())
    }

    /** post cu un nou artist */
    /** POST that handles "old" and "new" client requests */
    @PostMapping("/api/songcollection/artists")
    fun newArtist(@RequestBody newArtist: Artist): ResponseEntity<*> {
        val allowedRoles = listOf<Int>(3, 4)

        println(newArtist)
        val entityModel = artistAssembler.toModel(artistRepository.save(newArtist))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }

    /** get unui artist dupa id (daca exista) */
    @GetMapping("/api/songcollection/artists/{uuid}")
    fun one(@PathVariable uuid: String): EntityModel<Artist> {
        val artist = artistRepository.findById(uuid)
            .orElseThrow { Exception(uuid) }
        return artistAssembler.toModel(artist)
    }

    /** replace cu un nou artist, sau creaza unul cu acel id - dar nu schimba uuid-ul */
    /** Handling a PUT for different clients */
    @PutMapping("/api/songcollection/artists/{uuid}")
    fun replaceArtist(@RequestBody newArtist: Artist, @PathVariable uuid: String): ResponseEntity<*> {
        val allowedRoles = listOf<Int>(3, 4)


        val updatedArtist = artistRepository.findById(uuid)
            .map { artist: Artist ->
                artist.setName(newArtist.getName())
                artist.setActivity(newArtist.isActive())
                artistRepository.save(artist)
            }
            .orElseGet {
                newArtist.setUuid(uuid)
                artistRepository.save(newArtist)
            }
        val entityModel = artistAssembler.toModel(updatedArtist)

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }

    /** sterg artist dupa id */
    @DeleteMapping("/api/songcollection/artists/{uuid}")
    fun deleteArtist(@PathVariable uuid: String): ResponseEntity<*> {
        val allowedRoles = listOf<Int>(3, 4)

        try {
            artistRepository.deleteById(uuid)
            return ResponseEntity.noContent().build<Any>()
        } catch (ex: Exception) {
            return ResponseEntity.badRequest().body("NU  mere delete la artistttt")
        }
    }

    /** filtrez artistii */
    @GetMapping("/api/songcollection/artists/")
    fun filterAllSongs(
        @RequestParam(name = "name", defaultValue = "", required = false) name: Optional<String>,
        @RequestParam(name = "match", defaultValue = "partial", required = false) match: Optional<String>,
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "items_per_page", defaultValue = "1") items_per_page: Int,
    ): PagedModel<ArtistModel> {
        // paginare
        val pageable: Pageable = PageRequest.of(page, items_per_page);

        // filtered songs paginated
        val filteredArtistPage: Page<Artist> = artistService.getFilteredArtists(pageable, name, match)

        // return paginated result
        return pagedResourcesAssembler.toModel(filteredArtistPage, artistModelAssembler);
    }
}