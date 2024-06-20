package elevator;

import elevator.helper.FileHandler;
import elevator.helper.StateHandler;
import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;

import static it.unibs.fp.mylib.Strings.center;

/**
 * The {@code Main} class is the entry point of the elevator simulation program.
 */
public class Main {
    /**
     * The main method of the program.
     */
    public static void main() throws IOException, ClassNotFoundException {
        MyMenu menu = new MyMenu(center("Elevator Simulator", MyMenu.getFrameLength()),
                new String[]{"Load simulation state", "Start new simulation from file", "Start new simulation", "Help"});
        Elevator elevator = null;
        int choice;
        do {
            choice = menu.scegli();
            switch (choice) {
                case 1 -> elevator = loadPreviousState();
                case 2 -> elevator = setupSimulationFromFile();
                case 3 -> elevator = setupNewSimulation();
                case 4 -> displayHelp();
            }
        } while (choice == 4);

        if (elevator != null) {
            try {
                elevator.simulate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to initialize the elevator simulation.");
        }
    }

    /**
     * Sets up a new simulation from a file.
     *
     * @return an {@code Elevator} object representing the new simulation
     * @throws IOException if an I/O error occurs
     */
    private static Elevator setupSimulationFromFile() throws IOException {
        Building building = FileHandler.readBuildingSettings();
        Elevator elevator = new Elevator(building, FileHandler.readMaxPeople(), FileHandler.readInitialFloor(), FileHandler.readDirection());
        FileHandler.readPeopleData(elevator);
        return elevator;
    }

    /**
     * Sets up a new simulation by entering the building settings and people data.
     *
     * @return an {@code Elevator} object representing the new simulation
     */
    private static Elevator setupNewSimulation() {
        int floors = Input.readInt("Enter the number of floors: ");
        int bottomFloor = Input.readInt("Enter the bottom floor: ");
        Building building = new Building(floors + bottomFloor - 1, bottomFloor);
        Elevator elevator = new Elevator(building, Input.readInt("Enter the maximum number of people: "), Input.readInt("Enter the initial floor: "), Input.readNotEmptyString("Enter the initial direction (up/down): "));
        return enterPeopleData(elevator);
    }

    /**
     * Displays the help menu with the commands description.
     */
    private static void displayHelp() {
        System.out.println("Help, commands description:");
        System.out.println("1. Load simulation state: Load the previous state of the elevator simulation from a save file.");
        System.out.println("2. Start new simulation from file: Start a new simulation from a file, using the settings and input in resources/sim_settings folder.");
        System.out.println("3. Start new simulation: Start a new simulation by entering the building settings and people data.");
        System.out.println();
    }

    /**
     * Loads the previous state of the elevator simulation from a file.
     *
     * @return an {@code Elevator} object representing the previous state of the simulation
     * @throws IOException            if an I/O error occurs
     * @throws ClassNotFoundException if the class of the serialized object could not be found
     */
    private static Elevator loadPreviousState() throws IOException, ClassNotFoundException {
        String filename = Input.readNotEmptyString("Enter the filename to load: ");
        return StateHandler.loadState("./resources/saves/" + filename);
    }

    /**
     * Enters the people data for the elevator simulation.
     *
     * @param elevator the elevator object
     * @return the elevator object with the people data
     */
    private static Elevator enterPeopleData(Elevator elevator) {
        int i = 0;
        do {
            i++;
            int startFloor = Input.readInt("Enter the starting floor of person " + (i) + ": ", elevator.getMinFloor(), elevator.getMaxFloor());
            int destFloor = Input.readInt("Enter the destination floor of person " + (i) + ": ", elevator.getMinFloor(), elevator.getMaxFloor());
            if (startFloor == destFloor) {
                System.out.println("\n‼️Error: current floor and destination floor cannot be the same. Person skipped.\n");
                continue;
            }
            elevator.addPersonToWaitingList(new Person(startFloor, destFloor));
            if (!Input.yesOrNo("Do you want to add another person? (yes/no)")) {
                break;
            }
        } while (true);
        return elevator;
    }
}
