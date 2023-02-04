package com.spotifyapp.SpotifyProject.presentation.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
class DocumentationController {
    @GetMapping("/api/docs")
    fun getDocumentation(): String{
        val file = File("src/main/resources/documentation/openapi.json")
        val text = file.readText()
        return text
    }
}