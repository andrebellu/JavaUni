package archive;

/**
 * Class to manage the songs
 * @author Andrea Bellu
 */
public class Song {
    String title;
    double duration;
    int year;

    /**
     * Constructor for the Song class
     * @param title the title of the song
     * @param duration the duration of the song
     * @param year the year of the song
     */
    public Song(String title, double duration, int year) {
        this.title = title;
        this.duration = duration;
        this.year = year;
    }
}
