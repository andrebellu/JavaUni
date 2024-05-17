package archive;

import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static archive.Artist.checkArtist;
import static archive.Artist.readArtists;
import static it.unibs.fp.mylib.Strings.center;
import static it.unibs.fp.mylib.MyMenu.getFrameLength;

public class Main {
    static String[] voices = {"Artists management", "CDs management", "Play Music"};
    static String[] artistsVoices = {"Add artist", "Remove artist", "Update artist", "Show artists"};
    static String[] cdsVoices = {"Add CD", "Remove CD", "Update CD", "Show CDs"};
    static String[] playVoices = {"Play CD", "Play song", "Shuffle play"};


    public static void main(String[] args) throws IOException {
        MyMenu menu = new MyMenu(center("Archive", getFrameLength()), voices);
        ArrayList<CD> archive = new ArrayList<>();
        int choice;

        do {
            choice = menu.scegli();
            switch (choice) {
                case 1:
                    MyMenu artistsMenu = new MyMenu(center("Artists management", getFrameLength()), artistsVoices);
                    int artistsChoice;
                    do {
                        artistsChoice = artistsMenu.scegli();
                        switch (artistsChoice) {
                            case 1:
                                String name = Input.readNotEmptyString("Insert artist name: ").toLowerCase();
                                String surname = Input.readNotEmptyString("Insert artist surname: ").toLowerCase();
                                String nickname = Input.readNotEmptyString("Insert artist nickname: ").toLowerCase();
                                LocalDate birthDate = LocalDate.of(Input.readInt("Insert year of birth: "), Input.readInt("Insert month of birth: "), Input.readInt("Insert day of birth: "));
                                Artist.addArtist(new Artist(name, surname, nickname, birthDate));
                                break;
                            case 2:
                                String artistName = Input.readNotEmptyString("Insert artist name: ").toLowerCase();
                                Artist.removeArtist(artistName);
                                break;
                            case 3:
                                //Artist.updateArtist();
                                break;
                            case 4:
                                readArtists();
                                break;
                        }
                    } while (artistsChoice != 0);
                    break;
                case 2:
                    MyMenu cdsMenu = new MyMenu(center("CDs management", getFrameLength()), cdsVoices);
                    int cdsChoice;
                    do {
                        cdsChoice = cdsMenu.scegli();
                        switch (cdsChoice) {
                            case 1:
                                CD cd;
                                String title = Input.readNotEmptyString("Insert CD title: ").toLowerCase();

                                String nickname = Input.readNotEmptyString("Insert artist nickname, if it has no nickname write name and surname ").toLowerCase();

                                int year = Input.readInt("Insert year of release: ");

                                int line = checkArtist(nickname);
                                if (line != 0) {
                                    Artist artist = Artist.getArtist(line);
                                    cd = new CD(title, artist, year);
                                } else {
                                    System.out.println("Artist not found, please add the artist first");
                                    break;
                                }
                                do {
                                    String songTitle = Input.readNotEmptyString("Insert song title: ");
                                    int duration = Input.readInt("Insert song duration: ");
                                    int s_year = Input.readInt("Insert year of release: ");
                                    Song song = new Song(songTitle, duration, s_year);
                                    cd.addTrack(song);
                                } while (Input.yesOrNo("Do you want to add another song?"));

                                archive.add(cd);

                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                for (CD element : archive) {
                                    System.out.println(element.toString());
                                }
                        }
                    } while (cdsChoice != 0);
                    break;
                case 3:
                    MyMenu playMenu = new MyMenu(center("Play Music", getFrameLength()), playVoices);
                    int playChoice;
                    do {
                        playChoice = playMenu.scegli();
                        switch (playChoice) {
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                        }
                    } while (playChoice != 0);
                    break;
            }


        } while (choice != 0);
    }
}
