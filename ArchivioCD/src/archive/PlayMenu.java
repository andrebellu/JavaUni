package archive;

import it.unibs.fp.mylib.MyMenu;

import static it.unibs.fp.mylib.Strings.center;
import static it.unibs.fp.mylib.MyMenu.getFrameLength;

public class PlayMenu {
    private static final String[] playVoices = {"Play CD", "Play song", "Shuffle play"};

    public void show() {
        MyMenu playMenu = new MyMenu(center("Play Music", getFrameLength()), playVoices);
        int playChoice;
        do {
            playChoice = playMenu.scegli();
            switch (playChoice) {
                case 1:
                    // Implement the play CD functionality here
                    break;
                case 2:
                    // Implement the play song functionality here
                    break;
                case 3:
                    // Implement the shuffle play functionality here
                    break;
            }
        } while (playChoice != 0);
    }
}
