package test;

import archive.Artist;
import archive.CD;
import archive.Song;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Test class for the CD class.
 * It contains test cases to verify the functionality of the CD class methods.
 */
public class CDTest {

    /**
     * Tests the addTrack method.
     * It creates a CD, adds a song to it, and checks if the song was added successfully.
     */
    @Test
    public void testAddTrack() {
        Artist artist = new Artist("test_artist", LocalDate.of(1990, 1, 1));
        CD cd = new CD("test_cd", artist, 2020);
        Song song = new Song("test_song", 3.5, 2020);

        cd.addTrack(song);

        assertEquals(1, cd.tracks.size());
        assertEquals("test_song", cd.tracks.get(0).getTitle());
    }

    /**
     * Tests the removeTrack method.
     * It creates a CD, adds a song to it, removes the song, and checks if the song was removed successfully.
     */
    @Test
    public void testRemoveTrack() {
        Artist artist = new Artist("test_artist", LocalDate.of(1990, 1, 1));
        CD cd = new CD("test_cd", artist, 2020);
        Song song = new Song("test_song", 3.5, 2020);
        cd.addTrack(song);

        cd.removeTrack(song);

        assertEquals(0, cd.tracks.size());
    }

    /**
     * Tests the updateTrack method.
     * It creates a CD, adds a song to it, updates the song, and checks if the song was updated successfully.
     */
    @Test
    public void testUpdateTrack() {
        Artist artist = new Artist("test_artist", LocalDate.of(1990, 1, 1));
        CD cd = new CD("test_cd", artist, 2020);
        Song song = new Song("test_song", 3.5, 2020);
        cd.addTrack(song);

        Song updatedSong = new Song("test_song", 4.0, 2021);
        cd.updateTrack(updatedSong);

        assertEquals(1, cd.tracks.size());
        assertEquals(4.0, cd.tracks.get(0).getDuration(), 0.01);
    }

    /**
     * Tests the setDuration method.
     * It creates a CD, adds multiple songs to it, sets the duration, and checks if the total duration is correct.
     */
    @Test
    public void testSetDuration() {
        Artist artist = new Artist("test_artist", LocalDate.of(1990, 1, 1));
        CD cd = new CD("test_cd", artist, 2020);
        cd.addTrack(new Song("test_song_1", 3.5, 2020));
        cd.addTrack(new Song("test_song_2", 4.0, 2020));

        cd.setDuration();

        assertEquals(7.5, cd.getDuration(), 0.01);
    }
}
