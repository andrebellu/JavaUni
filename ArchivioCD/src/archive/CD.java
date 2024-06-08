package archive;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class to represent a CD
 * @author Andrea Bellu
 */
public class CD {
    public ArrayList<Song> tracks = new ArrayList<>();
    String title;
    Artist artist;
    double duration;
    int year;

    /**
     * Constructor
     * @param title the title of the CD
     * @param artist the artist of the CD
     * @param year the year of the CD
     */
    public CD(String title, Artist artist, int year) {
        this.title = title;
        this.artist = artist;
        this.year = year;
    }

    /**
     * Method to print the CD information, overriding the toString method
     * @return String with the CD information
     */
    @Override
    public String toString() {
        StringBuilder songsTitles = new StringBuilder();
        for (Song song : tracks) {
            songsTitles.append(song.title).append(", ");
        }
        return "CD: " + title + "\n" +
                "Artist: " + artist.getNickname() + "\n" +
                "Year: " + year + "\n" +
                "Tracks: " + songsTitles + "\n";

    }

    /**
     * Method to add a track to the CD
     * @param song the song to add
     */
    public void addTrack(Song song) {
        tracks.add(song);
    }

    /**
     * Method to remove a track from the CD
     * @param song the song to remove
     */
    public void removeTrack(Song song) {
        tracks.remove(song);
    }

    /**
     * Method to update a track
     * @param song the song to update
     */
    public void updateTrack(Song song) {
        for (Song s : tracks) {
            if (Objects.equals(s.title, song.title)) {
                s.duration = song.duration;
                s.year = song.year;
            }
        }
    }

    /**
     * Method to get the CD title
     * @return the title of the CD
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set the duration of the CD
     */
    public void setDuration() {
        for (Song song : tracks) {
            this.duration += song.duration;
        }
    }

    /**
     * Method to get the duration of the CD
     * @return the duration of the CD
     */
    public double getDuration() {
        return duration;
    }
}
