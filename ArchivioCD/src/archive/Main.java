package archive;

import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;
import java.util.ArrayList;

import static it.unibs.fp.mylib.Strings.center;
import static it.unibs.fp.mylib.MyMenu.getFrameLength;

public class Main {
    private static final String[] voices = {"Artists management", "CDs management", "Play Music"};

    public static void main(String[] args) throws IOException {
        MyMenu menu = new MyMenu(center("Archive", getFrameLength()), voices);
        ArrayList<CD> archive = new ArrayList<>();
        int choice;

        ArtistsMenu artistsMenu = new ArtistsMenu();
        CDsMenu cdsMenu = new CDsMenu(archive);
        PlayMenu playMenu = new PlayMenu();

        do {
            choice = menu.scegli();
            switch (choice) {
                case 1:
                    artistsMenu.show();
                    break;
                case 2:
                    cdsMenu.show();
                    break;
                case 3:
                    playMenu.show();
                    break;
            }
        } while (choice != 0);
    }
}
