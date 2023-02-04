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

import com.spotifyapp.SpotifyProject.business.assemblers.SongAssembler
import com.spotifyapp.SpotifyProject.business.services.SongService
import com.spotifyapp.SpotifyProject.data.entities.Song
import com.spotifyapp.SpotifyProject.data.models.SongModel
import com.spotifyapp.SpotifyProject.business.assemblers.SongModelAssembler
import com.spotifyapp.SpotifyProject.persistence.repositories.SongRepository
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
class SongController() {

    @Autowired
    private lateinit var songRepository: SongRepository

    @Autowired
    private lateinit var songAssembler: SongAssembler

    @Autowired
    private lateinit var songService: SongService

    /** TUTORIAL FOLOSIT:
     * https://www.springcloud.io/post/2022-04/hateoas-spring-boot-and-jpa/#gsc.tab=0 */
    @Autowired
    private lateinit var pagedResourcesAssembler: PagedResourcesAssembler<Song>

    @Autowired
    private lateinit var songModelAssembler: SongModelAssembler


    /** obtin toate melodiile */
    @GetMapping("/api/songcollection/songs")
    fun all(): CollectionModel<EntityModel<Song>> {
        val songs = songService.getAllSongsAndAlbums().stream()
            .map { song: Song -> songAssembler.toModel(song) }
            .collect(Collectors.toList())

        return CollectionModel.of(songs, linkTo(methodOn(SongController::class.java).all()).withSelfRel())
    }

    /** post cu un nou song */
    /** POST that handles "old" and "new" client requests */
    @PostMapping("/api/songcollection/songs")
    fun newSong(@RequestBody newSong: Song): ResponseEntity<*> {
        val allowedRoles = listOf<Int>(3, 4)
        // check if current user is the owner of the song/album
        // and add it to allowedRoles


        val entityModel = songAssembler.toModel(songRepository.save(newSong))
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }

    /** get unui song dupa id (daca exista) */
    @GetMapping("/api/songcollection/songs/{id}")
    fun one(@PathVariable id: Int): EntityModel<Song> {
        val song = songRepository.findById(id)
            .orElseThrow { Exception(id.toString()) }
        return songAssembler.toModel(song)
    }

    /** replace cu un nou song, sau creaza unul cu acel id - dar nu schimba id-ul */
    /** Handling a PUT for different clients */
    @PutMapping("/api/songcollection/songs/{id}")
    fun replaceSong(@RequestBody newSong: Song, @PathVariable id: Int): ResponseEntity<*> {
        val updatedSong = songRepository.findById(id)
            .map { song: Song ->
                song.setName(newSong.getName())
                song.setGenre(newSong.getGenre())
                song.setReleaseYear(newSong.getReleaseYear())
                song.setType(newSong.getType())
                song.setParentId(newSong.getParentId())
                song.setName(newSong.getName())
                songRepository.save(song)
            }
            .orElseGet {
                newSong.setId(id)
                songRepository.save(newSong)
            }
        val entityModel = songAssembler.toModel(updatedSong)

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel)
    }

    /** sterg song dupa id */
    @DeleteMapping("/api/songcollection/songs/{id}")
    fun deleteSong(@PathVariable id: Int): ResponseEntity<*> {
        try {
            songRepository.deleteById(id)
            return ResponseEntity.noContent().build<Any>()
        } catch (ex: Exception) {
            return ResponseEntity.badRequest().body("NU  mere in song controller delete")
        }
    }

    /** filtrez melodiile */
    @GetMapping("/api/songcollection/songs/")
    fun filterAllSongs(
        @RequestParam(name = "title", defaultValue = "", required = false) title: Optional<String>,
        @RequestParam(name = "genre", defaultValue = "", required = false) genre: Optional<String>,
        @RequestParam(name = "releaseYear", defaultValue = "-1", required = false) releaseYear: Optional<Int>,
        @RequestParam(name = "match", defaultValue = "partial", required = false) match: Optional<String>,
        @RequestParam(name = "page", defaultValue = "0") page: Int,
        @RequestParam(name = "items_per_page", defaultValue = "1") items_per_page: Int,
    ): PagedModel<SongModel> {
        // paginare
        val pageable: Pageable = PageRequest.of(page, items_per_page);

        // filtered songs paginated
        val filteredSongPage: Page<Song> =
            songService.getFilteredSongsAndAlbums(pageable, title, genre, releaseYear, match)

        // return paginated result
        return pagedResourcesAssembler.toModel(filteredSongPage, songModelAssembler);
    }
}