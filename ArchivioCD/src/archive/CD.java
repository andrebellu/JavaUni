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

    @Override
    public String toString() {
        return "CD{" +
                "tracks=" + tracks.toString() +
                ", title='" + title + '\'' +
                ", artist=" + artist +
                ", duration=" + duration +
                ", year=" + year +
                '}';
    }

    public void addTrack(Song song){
        tracks.add(song);
    }

    public void removeTrack(Song song){
        tracks.remove(song);
    }

    public void updateTrack(Song song){
        for (Song s : tracks){
            if (s.title == song.title){
                s = song;
            }
        }
    }


}
