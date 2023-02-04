package com.gateway.spotifyapp.presentation.controllers

import com.gateway.spotifyapp.business.services.JSONOperations
import com.gateway.spotifyapp.business.services.TokenOperations
import com.gateway.spotifyapp.business.services.UrlCaller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000/"])
class SongController {

    @Autowired
    private lateinit var urlCaller: UrlCaller

    @Autowired
    private lateinit var tokenVerifier: TokenOperations

    @Autowired
    private lateinit var jsonOperations: JSONOperations

//    l-am folosit cand urmaream tutorialul, nu-l mai folosesc. il pastrez doar ca dovada ca am incercat tutorialul
//    @Autowired
//    private lateinit var tokenService: TokenService

    // todo: in mod normal, ar trebui ca pentru fiecare operatie Create/Update/Delete sa existe verificari de tipul
    //  if content manager (3): execute call
    //  else if artist and owner:
    //     artist verificam usor din bearerJws daca are rolul artist (2),
    //     pentru a verifica daca e owner-ul melodiei, trebuie ca in artists_songs_albums sa existe entry-ul de tipul
    //         select * from artists_songs_albums where uuid = 'uuid_dat' AND id = 'id_melodie/album vrut
    //     execute call

    // restul de request-uri de tip CUD sunt similare celui de jos, unde se face request resursei vrute in functie
    // de uri-ul transmis (val url = "http://localhost:8080/api/songcollection/songs", de exemplu)

    // similar, s-ar realiza securizarea si pentru ArtistController,
    // doar ca acolo ar fi doar pentru ContentManager de facut verificarile
    // si de creat noile rute

    // iar pentru playlist ar trebui operatii de tip CRUD astfel:
    // in playlist se afla uuid, iar in token se afla si acolo un uuid. daca sunt identice,
    // inseamna ca acel client este chiar owner-ul playlist-ului, caz in care continua,
    // iar in cazul in care nu e owner, pur si simplu nu se vor realiza functionalitatile vrute de el

    @GetMapping("/api/songcollection/songs")
    fun all(@RequestHeader(value = "Authorization") bearerJws: String): Any {

        // url to call and available roles for that uri
        val url = "http://localhost:8080/api/songcollection/songs"
        val allowedRoles = listOf(0, 1, 2, 3, 4)

        // verify if token is valid and roles are satisfied
        if (tokenVerifier.verifyToken(bearerJws, allowedRoles) == "TOKEN FAILED")
            return "Token couldn't be Authorized or is not Valid."

        // get the response string in pretty json if it's parsable or raw string
        val responseString = urlCaller.getResponse(url)
        // todo: responseString contine toate melodiile cand este satisfacut token-ul
        //  metoda aceasta folosita in scop demonstrativ, celelalte metode realizandu-se intr-o maniera similara

        // todo: mai jos este un exemplu de decodare a bearerJws-ului dat ca parametru in header
        //  tot in scop demonstrativ, am furnizat un exemplu de un astfel de json pentru atunci cand se doresc a se
        //  extrage diverse campuri din el, precum rolurile sau uuid-ul
        // decode the token
        val decoder = Base64.getDecoder()

        val pieces: List<String> = bearerJws.split(".")
        val b64payload = pieces[1]

        // user json
        val payloadJSON = String(decoder.decode(b64payload))
        val prettyJSON = jsonOperations.prettyJSONString(payloadJSON)

        val userRoles = jsonOperations.geetRolesFromJSONString(payloadJSON)
        allowedRoles.forEach {
            if (userRoles.contains(it)){
                // the actual response if it was allowed
                println(responseString)
                return responseString
            }
        }

        return "Not allowed"
    }
}