/**
 * https://springframework.guru/configuring-spring-boot-for-mariadb/
 *
 *
 * PENTRU OPENAPI:
 * default este asta
 * http://localhost:8080/v3/api-docs
 *
 * trebuie setat din .properties locatia la care sa fie generat documentul
 * http://localhost:8080/api/docs
 *
 * apoi prin swagger editor il obtii, din yml in json si il pui pentru ruta respectiva
 */

package com.spotifyapp.SpotifyProject

//import io.swagger.v3.oas.annotations.OpenAPIDefinition
//import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@OpenAPIDefinition(info = Info(title = "Spotify App API", version = "1.2", description = "Spotify Application"))
class SpotifyProjectApplication

fun main(args: Array<String>) {
	runApplication<SpotifyProjectApplication>(*args)
}
