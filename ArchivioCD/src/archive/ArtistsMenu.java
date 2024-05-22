package archive;
import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;
import java.time.LocalDate;

import static archive.Artist.readArtists;
import static it.unibs.fp.mylib.Strings.center;
import static it.unibs.fp.mylib.MyMenu.getFrameLength;

/**
 * Class to manage the artists menu
 * @autor Andrea Bellu
 */
public class ArtistsMenu {
    private static final String[] artistsVoices = {"Add artist", "Remove artist", "Update artist", "Show artists"};

    /**
     * Method to show the artists menu
     * @throws IOException if an I/O error occurs
     */
    public void show() throws IOException {
        MyMenu artistsMenu = new MyMenu(center("Artists management", getFrameLength()), artistsVoices);
        int artistsChoice;
        do {
            artistsChoice = artistsMenu.scegli();
            switch (artistsChoice) {
                case 1:
                    String nickname = Input.readNotEmptyString("Insert artist name: ").toLowerCase();
                    LocalDate birthDate = LocalDate.of(Input.readInt("Insert year of birth: "), Input.readInt("Insert month of birth: "), Input.readInt("Insert day of birth: "));
                    Artist.addArtist(new Artist(nickname, birthDate));
                    break;
                case 2:
                    String artistName = Input.readNotEmptyString("Insert artist name: ").toLowerCase();
                    Artist.removeArtist(artistName);
                    break;
                case 3:
                    Artist.updateArtist();
                    break;
                case 4:
                    readArtists();
                    break;
            }
        } while (artistsChoice != 0);
    }
}
