package archive;

import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;
import java.util.ArrayList;

import static archive.Artist.checkArtist;
import static it.unibs.fp.mylib.Strings.center;
import static it.unibs.fp.mylib.MyMenu.getFrameLength;

/**
 * Class to manage the CDs menu
 * @autor Andrea Bellu
 */
public class CDsMenu {
    private static final String[] cdsVoices = {"Add CD", "Remove CD", "Update CD", "Remove track", "Show CDs"};
    public static ArrayList<CD> archive;

    /**
     * Constructor
     * @param archive the archive of CDs
     */
    public CDsMenu(ArrayList<CD> archive) {
        this.archive = archive;
    }

    /**
     * Method to show the CDs menu
     * @throws IOException if an I/O error occurs
     */
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
                        double duration = Input.readDouble("Insert song duration: ");
                        int s_year = Input.readInt("Insert year of release: ");
                        Song song = new Song(songTitle, duration, s_year);
                        cd.addTrack(song);
                    } while (Input.yesOrNo("Do you want to add another song?"));

                    cd.setDuration();
                    archive.add(cd);
                    break;
                case 2:
                    String cdTitle = Input.readNotEmptyString("Insert CD title: ").toLowerCase();
                    removeCD(cdTitle);
                    break;
                case 3:
                    String cdTitleToUpdate = Input.readNotEmptyString("Insert CD title: ").toLowerCase();
                    for (CD element : archive) {
                        if (element.getTitle().equals(cdTitleToUpdate)) {
                            do {
                                String songTitle = Input.readNotEmptyString("Insert song title: ");
                                double duration = Input.readDouble("Insert song duration: ");
                                int s_year = Input.readInt("Insert year of release: ");
                                Song song = new Song(songTitle, duration, s_year);
                                element.addTrack(song);
                            } while (Input.yesOrNo("Do you want to add another song?"));
                            break;
                        }
                    }
                    break;
                case 4:
                    String cdTitleToRemoveTrack = Input.readNotEmptyString("Insert CD title: ").toLowerCase();
                    for (CD element : archive) {
                        if (element.getTitle().equals(cdTitleToRemoveTrack)) {
                            String songTitleToRemove = Input.readNotEmptyString("Insert song title: ");
                            for (Song song : element.tracks) {
                                if (song.title.equals(songTitleToRemove)) {
                                    element.removeTrack(song);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    break;
                case 5:
                    for (CD element : archive) {
                        System.out.println(element.toString());
                    }
                    break;
            }
        } while (cdsChoice != 0);
    }

    public void removeCD(String cdTitle) {
        for (CD element : archive) {
            if (element.getTitle().equals(cdTitle)) {
                archive.remove(element);
                break;
            }
        }
    }
}
