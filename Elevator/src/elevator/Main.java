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
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        final String[] VOICES = {"Load simulation state", "Start new simulation from file", "Start new simulation"};
        final String TITLE = "Elevator Simulator";
        MyMenu menu = new MyMenu(center(TITLE, MyMenu.getFrameLength()), VOICES);
        Elevator elevator = null;

        try {
            int choice = menu.scegli();
            switch (choice) {
                case 1 -> elevator = loadPreviousState();
                case 2 -> {
                    Building building = FileHandler.readBuildingSettings();
                    elevator = new Elevator(building, FileHandler.readMaxPeople(), FileHandler.readInitialFloor(), FileHandler.readDirection());
                    FileHandler.readPeopleData(elevator);
                }
                case 3 -> {
                    Building building = new Building(Input.readInt("Enter the number of floors: "), Input.readInt("Enter the bottom floor: "));
                    elevator = new Elevator(building, Input.readInt("Enter the maximum number of people: "), Input.readInt("Enter the initial floor: "), Input.readNotEmptyString("Enter the initial direction (up/down): "));
                    elevator = enterPeopleData(elevator);
                }
            }

            if (elevator != null) {
                elevator.simulate();
            } else {
                System.out.println("Failed to initialize the elevator simulation.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
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
