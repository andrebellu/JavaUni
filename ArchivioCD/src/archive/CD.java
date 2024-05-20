package archive;

import java.util.ArrayList;

public class CD {
    ArrayList<Song> tracks = new ArrayList<>();
    String title;
    Artist artist;
    int duration;
    int year;

    public CD(String title, Artist artist, int year) {
        this.title = title;
        this.artist = artist;
        this.year = year;
    }

    public CD() {
    }

    @Override
    public String toString() {
        String songsTitles = "";
        for (Song song : tracks) {
            songsTitles += song.title + ", ";
        }
        return "CD: " + title + "\n" +
                "Artist: " + artist.getNickname() + "\n" +
                "Year: " + year + "\n" +
                "Tracks: " + songsTitles + "\n";

    }

    public void addTrack(Song song) {
        tracks.add(song);
    }

    public void removeTrack(Song song) {
        tracks.remove(song);
    }

    public void updateTrack(Song song) {
        for (Song s : tracks) {
            if (s.title == song.title) {
                s = song;
            }
        }
    }


}
