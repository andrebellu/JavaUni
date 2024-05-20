package archive;

import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;
import java.util.ArrayList;

import static archive.Artist.checkArtist;
import static it.unibs.fp.mylib.Strings.center;
import static it.unibs.fp.mylib.MyMenu.getFrameLength;

public class CDsMenu {
    private static final String[] cdsVoices = {"Add CD", "Remove CD", "Update CD", "Show CDs"};
    private ArrayList<CD> archive;

    public CDsMenu(ArrayList<CD> archive) {
        this.archive = archive;
    }

    public void show() throws IOException {
        MyMenu cdsMenu = new MyMenu(center("CDs management", getFrameLength()), cdsVoices);
        int cdsChoice;
        do {
            cdsChoice = cdsMenu.scegli();
            switch (cdsChoice) {
                case 1:
                    CD cd;
                    String title = Input.readNotEmptyString("Insert CD title: ").toLowerCase();

                    String nickname = Input.readNotEmptyString("Insert artist nickname: ").toLowerCase();

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
                    // Implement
                    break;
                case 3:
                    // Implement
                    break;
                case 4:
                    for (CD element : archive) {
                        System.out.println(element.toString());
                    }
                    break;
            }
        } while (cdsChoice != 0);
    }
}
