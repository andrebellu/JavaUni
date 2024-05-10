package archive;

import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;
import java.time.LocalDate;

import static archive.Artist.readArtists;
import static it.unibs.fp.mylib.Strings.center;
import static it.unibs.fp.mylib.MyMenu.getFrameLength;

public class Main {
    static String[] voices = {"Artists management", "CDs management", "Songs management", "Play Music"};
    static String[] artistsVoices = {"Add artist", "Remove artist", "Update artist", "Show artists"};
    static String[] cdsVoices = {"Add CD", "Remove CD", "Update CD", "Show CDs"};
    static String[] playVoices = {"Play CD", "Play song", "Shuffle play"};


    public static void main(String[] args) throws IOException {
        MyMenu menu = new MyMenu(center("Archive", getFrameLength()), voices);
        Archive archive = new Archive();
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

                        }
                    } while (cdsChoice != 0);
                    break;
                case 3:
                    MyMenu playMenu = new MyMenu(center("Songs management", getFrameLength()), playVoices);
                    int playChoice;
                    do {
                        playChoice = playMenu.scegli();
                        switch (playChoice) {
                        }
                    } while (playChoice != 0);
                    break;
                case 4:
                    break;
            }



        } while (choice != 0);
    }
}
