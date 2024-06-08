package test;

import archive.Song;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the Song class.
 * It contains test cases to verify the functionality of the Song class methods.
 */
public class SongTest {

    /**
     * Tests the creation of a Song object.
     * It creates a song and verifies that the song's title, duration, and year are set correctly.
     */
    @Test
    public void testSongCreation() {
        Song song = new Song("test_song", 3.5, 2020);

        assertEquals("test_song", song.getTitle());
        assertEquals(3.5, song.getDuration(), 0.01);
        assertEquals(2020, song.getYear());
    }
}
