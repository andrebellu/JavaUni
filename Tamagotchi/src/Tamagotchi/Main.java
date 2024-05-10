package Tamagotchi;

import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import static it.unibs.fp.mylib.BelleStringhe.*;

/**
 * The main class for the Tamagotchi Simulator program.
 * This class handles the user interface and interacts with the Tamagotchi class.
 */
public class Main {

    static String[] voices = {"Create a new Tamagotchi",
            "Give Cookies",
            "Pet",
            "View Tamagotchi's Status",
            "Release Tamagotchi"};
    static String[] reducedVoices = {
            "View Tamagotchi's Status",
            "Release Tamagotchi"};
    static String title = "Tamagotchi Simulator";
    static String deadTitle = "Tamagotchi Simulator - Your Tamagotchi is dead!";

    /**
     * The main entry point for the program.
     * This method runs the Tamagotchi Simulator loop, displaying the menu and handling user choices.
     *
     * @param args The command line arguments (unused in this program).
     */
    public static void main(String[] args) {
        int choice;
        Tamagotchi tamagotchi = new Tamagotchi(" ", 50, 50);
        MyMenu longMenu = new MyMenu(center(title, MyMenu.getFrameLength()), voices);
        MyMenu reducedMenu = new MyMenu(center(deadTitle, MyMenu.getFrameLength()), reducedVoices);
        MyMenu menu = longMenu;
        do {
            choice = menu.scegli();

            if (!tamagotchi.getName().equals(" ") && tamagotchi.dead() && choice == 1) {
                choice = 4;
            } else if (!tamagotchi.getName().equals(" ") && tamagotchi.dead() && choice == 2) {
                choice = 5;
            }

            switch (choice) {
                case 1:
                    if (tamagotchi.getName().equals(" ")) {
                        String name = Input.readString("Enter your Tamagotchi's name: ");
                        tamagotchi = new Tamagotchi(name);
                        tamagotchi.simulateLife();
                        tamagotchi.isDead();
                    } else {
                        System.out.println("You already have a Tamagotchi!");
                    }
                    break;
                case 2:
                    if (!tamagotchi.getName().equals(" ") && !tamagotchi.dead()) {
                        int cookies = Input.readPositiveInt("Enter the number of cookies: ");
                        int[] results = tamagotchi.giveCookie(cookies);
                        System.out.println("Gave " + results[0] + " cookies, hunger is now " + results[1]);
                        tamagotchi.dead();
                    } else {
                        printTamagotchiStatusMessage(tamagotchi);
                    }
                    break;
                case 3:
                    if (!tamagotchi.getName().equals(" ") && !tamagotchi.dead()) {
                        int pets = Input.readPositiveInt("Enter the number of pets: ");
                        int[] results = tamagotchi.pet(pets);
                        System.out.println("Pet " + results[0] + " times, happiness is now " + results[1]);
                    } else {
                        printTamagotchiStatusMessage(tamagotchi);
                    }
                    break;
                case 4:
                    if (!tamagotchi.getName().equals(" ")) {
                        tamagotchi.viewStatus();
                    } else {
                        System.out.println("You don't have a Tamagotchi yet!");
                    }
                    break;
                case 5:
                    System.out.println(tamagotchi.getName() + " has been released!");
                    tamagotchi = new Tamagotchi(" ", 50, 50);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    tamagotchi.stop();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            menu = tamagotchi.dead() ? reducedMenu : longMenu;
        } while (true);
    }

    private static void printTamagotchiStatusMessage(Tamagotchi tamagotchi) {
        if (tamagotchi.getName().equals(" ")){
            System.out.println("You don't have a Tamagotchi yet!");
        } else if (tamagotchi.dead()) {
            System.out.println("Your Tamagotchi is dead!");
        }
    }
}
