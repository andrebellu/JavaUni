package test;

import archive.Artist;
import org.junit.Test;
import java.io.IOException;
import java.time.LocalDate;
import static org.junit.Assert.*;

/**
 * Test class for the Artist class.
 * It contains test cases to verify the functionality of the Artist class methods.
 */
public class ArtistTest {

    /**
     * Tests the addArtist method.
     * It creates an artist, adds it to the file, and checks if it was added successfully.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testAddArtist() throws IOException {
        Artist artist = new Artist("test_artist", LocalDate.of(1990, 1, 1));
        Artist.addArtist(artist);

        assertNotEquals(0, Artist.checkArtist("test_artist"));
        Artist.removeArtist("test_artist");
    }

    /**
     * Tests the removeArtist method.
     * It creates an artist, adds it to the file, removes it, and checks if it was removed successfully.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testRemoveArtist() throws IOException {
        Artist artist = new Artist("test_artist", LocalDate.of(1990, 1, 1));
        Artist.addArtist(artist);

        Artist.removeArtist("test_artist");

        assertEquals(0, Artist.checkArtist("test_artist"));
    }

    /**
     * Tests the checkArtist method.
     * It creates an artist, adds it to the file, and checks if it exists in the file.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testCheckArtist() throws IOException {
        Artist artist = new Artist("test_artist", LocalDate.of(1990, 1, 1));
        Artist.addArtist(artist);

        assertNotEquals(0, Artist.checkArtist("test_artist"));

        Artist.removeArtist("test_artist");
    }

    /**
     * Tests the getArtist method.
     * It creates an artist, adds it to the file, retrieves it by its line number, and verifies the details.
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testGetArtist() throws IOException {
        Artist artist = new Artist("test_artist", LocalDate.of(1990, 1, 1));
        Artist.addArtist(artist);

        Artist retrievedArtist = Artist.getArtist(Artist.checkArtist("test_artist"));
        assertNotNull(retrievedArtist);
        assertEquals("test_artist", retrievedArtist.getNickname());

        Artist.removeArtist("test_artist");
    }
}
