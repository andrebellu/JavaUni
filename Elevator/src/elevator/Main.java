package elevator;

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
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        final String[] VOICES = {"Load simulation state", "Start new simulation"};
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
     * @return an {@code Elevator} object representing the previous state of the simulation
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of the serialized object could not be found
     */
    private static Elevator loadPreviousState() throws IOException, ClassNotFoundException {
        String filename = Input.readNotEmptyString("Enter the filename to load: ");
        return StateHandler.loadState("./resources/saves/" + filename);
    }
}
