// https://springframework.guru/configuring-spring-boot-for-mariadb/

package com.spotifyapp.SpotifyProject

import com.spotifyapp.SpotifyProject.persistence.repositories.ArtistRepository

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner::class)
@SpringBootTest
class ArtistRepositoryTest {
    @Autowired
    private lateinit var artistRepository: ArtistRepository

    @Before
    @Throws(Exception::class)
    fun setUp() {
//        val artist1 = Artist("5843848844", "Alice", false)
//        val artist2 = Artist("gkjgid93j94jf", "Bob", true)

        //save artist, verify has ID value after save
//        assertNull(artist1.getUuid())
//        assertNull(artist2.getUuid()) //null before save

//        artistRepository.save(artist1)
//        artistRepository.save(artist2)
//
//        assertNotNull(artist1.getUuid())
//        assertNotNull(artist2.getUuid())
    }

    @Test
    fun testFetchData() {
//        /*Test data retrieval*/
//        val artistA: Artist = artistRepository.findByName("FlorinVegan")
//        assertNotNull(artistA)
//        assertEquals("4hb8gr4hg89ubj", artistA.getUuid())
    }

//    @Test
//    fun testCount(){
//        /*Get all products, list should only have two*/
//        val artists: Iterable<Artist> = artistRepository.findAll()
//        var count = 0
//        for (artist in artists) {
//            count++
//        }
//        assertEquals(count, 2)
//    }
}
