package archive;

import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.util.Random;

import static it.unibs.fp.mylib.Strings.capitalize;
import static it.unibs.fp.mylib.Strings.center;
import static it.unibs.fp.mylib.MyMenu.getFrameLength;

/**
 * Class to manage the play menu
 * @author Andrea Bellu
 */
public class PlayMenu {
    private static final String[] playVoices = {"Play CD", "Play song", "Shuffle play"};

    /**
     * Method to show the play menu
     */
    public void show() {
        MyMenu playMenu = new MyMenu(center("Play Music", getFrameLength()), playVoices);
        int playChoice;
        do {
            playChoice = playMenu.scegli();
            switch (playChoice) {
                case 1:
                    String title = Input.readNotEmptyString("Enter the title of the CD you want to play: ");
                    for (CD element : CDsMenu.archive) {
                        if (element.getTitle().equals(title)) {
                            System.out.println("Listening to " + element.getTitle() + " by " + element.artist.getNickname() + "...");
                            break;
                        }
                    }
                    break;
                case 2:
                    String songTitle = Input.readNotEmptyString("Enter the title of the song you want to play: ");
                    for (CD element : CDsMenu.archive) {
                        for (Song song : element.tracks) {
                            if (song.title.equals(songTitle)) {
                                System.out.println("Listening to " + song.title + " by " + element.artist.getNickname() + "from the album " + element.getTitle() + "...");
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println("Shuffling play...");
                    Random random = new Random();
                    CD randomCD = CDsMenu.archive.get(random.nextInt(CDsMenu.archive.size()));
                    Song randomSong = randomCD.tracks.get(random.nextInt(randomCD.tracks.size()));
                    System.out.println("Listening to " + randomSong.title + " by " + capitalize(randomCD.artist.getNickname()) + " from the album " + randomCD.getTitle() + "...");
                    break;
            }
        } while (playChoice != 0);
    }
}
