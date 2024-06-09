package elevator;

import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final String [] VOICES = {"Load simulation state", "Start new simulation"};
        final String TITLE = "Elevator Simulator";
        MyMenu menu = new MyMenu(TITLE, VOICES);

        Scanner scanner = new Scanner(System.in);
        Elevator elevator;

        String filename = "./resources/saves/";

        if (shouldLoadPreviousState(scanner)) {
            filename += getFilename(scanner, "load from");
            elevator = StateHandler.loadState(filename);
        } else {
            Building building = FileHandler.readBuildingSettings();
            elevator = new Elevator(building, FileHandler.readMaxPersons(), FileHandler.readInitialFloor(), FileHandler.readDirection());
            FileHandler.readPeopleData(elevator);
        }

        elevator.simulate();

        if (shouldSaveCurrentState(scanner)) {
            filename += getFilename(scanner, "save to");
            StateHandler.saveState(elevator, filename);
        }

        scanner.close();
    }

    private static boolean shouldLoadPreviousState(Scanner scanner) {
        System.out.println("Do you want to load the previous simulation state? (yes/no): ");
        String loadChoice = scanner.nextLine().trim().toLowerCase();
        return loadChoice.equals("yes");
    }

    private static boolean shouldSaveCurrentState(Scanner scanner) {
        System.out.println("Do you want to save the current simulation state? (yes/no): ");
        String saveChoice = scanner.nextLine().trim().toLowerCase();
        return saveChoice.equals("yes");
    }

    private static String getFilename(Scanner scanner, String action) {
        System.out.println("Enter the filename to " + action + ": ");
        return scanner.nextLine().trim();
    }
}
