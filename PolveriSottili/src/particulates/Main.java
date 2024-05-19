package particulates;

import it.unibs.fp.mylib.MyMenu;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.unibs.fp.mylib.Strings.center;

/**
 * This class represents the main entry point of the program for managing particulate data.
 * @author Andrea Bellu
 */
public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String TITLE = "Particulates in the air";
    private static final String[] VOICES = {"Enter data", "Show data", "Show mean threshold", "Show max threshold", "Show if max threshold is exceeded", "Show if mean threshold is exceeded", "Settings"};

    public static void main(String[] args) {
        Particulates particulates = new Particulates();
        MenuHandler menuHandler = new MenuHandler(particulates);

        MyMenu menu = new MyMenu(center(TITLE, MyMenu.getFrameLength()), VOICES);
        int choice;
        do {
            choice = menu.scegli();
            menuHandler.handleMenuChoice(choice);
            if (choice != 0) {
                waitForEnter();
            }
        } while (choice != 0);
    }

    public static void waitForEnter() {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while waiting for input", e);
        }
    }
}

