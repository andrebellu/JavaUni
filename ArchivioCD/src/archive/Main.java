package archive;

import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;
import java.util.ArrayList;

import static it.unibs.fp.mylib.Strings.center;
import static it.unibs.fp.mylib.MyMenu.getFrameLength;

/**
 * Main class to manage the archive
 * @autor Andrea Bellu
 */
public class Main {
    private static final String[] voices = {"Artists management", "CDs management", "Play Music"};

    /**
     * Main method
     * @param args the command line arguments (unused)
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        MyMenu menu = new MyMenu(center("Archive", getFrameLength()), voices);
        ArrayList<CD> archive = new ArrayList<>();
        int choice;

        ArtistsMenu artistsMenu = new ArtistsMenu();
        CDsMenu cdsMenu = new CDsMenu(archive);
        PlayMenu playMenu = new PlayMenu();

        System.out.println("Welcome to the Archive");
        if (Input.yesOrNo("Do you want to load the demo archive?")) {
            demoCds(archive);
        }

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

    /**
     * Method to load the demo archive, CDs are generated with some songs
     * @param archive the archive of CDs to fill
     * @throws IOException if an I/O error occurs
     */
    private static void demoCds(ArrayList<CD> archive) throws IOException {
        int linep = Artist.checkArtist("Pink Floyd");
        Artist pink = Artist.getArtist(linep);

        int lineb = Artist.checkArtist("The Beatles");
        Artist beatles = Artist.getArtist(lineb);


        CD cd1 = new CD("The Dark Side of the Moon", pink, 1973);
        cd1.addTrack(new Song("Speak to Me", 1.07, 1973));
        cd1.addTrack(new Song("Breathe", 2.43, 1973));
        cd1.addTrack(new Song("On the Run", 3.35, 1973));
        cd1.addTrack(new Song("Time", 6.53, 1973));
        cd1.addTrack(new Song("The Great Gig in the Sky", 4.15, 1973));
        cd1.addTrack(new Song("Money", 6.22, 1973));
        cd1.addTrack(new Song("Us and Them", 7.51, 1973));
        cd1.addTrack(new Song("Any Colour You Like", 3.26, 1973));
        cd1.addTrack(new Song("Brain Damage", 3.50, 1973));
        cd1.addTrack(new Song("Eclipse", 2.03, 1973));
        cd1.setDuration();

        CD cd2 = new CD("Abbey Road", beatles, 1969);
        cd2.addTrack(new Song("Come Together", 4.20, 1969));
        cd2.addTrack(new Song("Something", 3.03, 1969));
        cd2.addTrack(new Song("Maxwell's Silver Hammer", 3.27, 1969));
        cd2.addTrack(new Song("Oh! Darling", 3.27, 1969));
        cd2.addTrack(new Song("Octopus's Garden", 2.51, 1969));
        cd2.addTrack(new Song("I Want You (She's So Heavy)", 7.47, 1969));
        cd2.addTrack(new Song("Here Comes the Sun", 3.05, 1969));
        cd2.addTrack(new Song("Because", 2.45, 1969));
        cd2.addTrack(new Song("You Never Give Me Your Money", 4.02, 1969));
        cd2.addTrack(new Song("Sun King", 2.26, 1969));
        cd2.addTrack(new Song("Mean Mr. Mustard", 1.06, 1969));
        cd2.addTrack(new Song("Polythene Pam", 1.12, 1969));
        cd2.addTrack(new Song("She Came in Through the Bathroom Window", 1.57, 1969));
        cd2.addTrack(new Song("Golden Slumbers", 1.31, 1969));
        cd2.addTrack(new Song("Carry That Weight", 1.36, 1969));
        cd2.addTrack(new Song("The End", 2.20, 1969));
        cd2.addTrack(new Song("Her Majesty", 0.23, 1969));
        cd2.setDuration();

        archive.add(cd1);
        archive.add(cd2);

    }
}
