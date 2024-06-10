package elevator;

import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.io.IOException;

import static it.unibs.fp.mylib.Strings.center;

public class Main {
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
                    elevator = new Elevator(building, FileHandler.readMaxPersons(), FileHandler.readInitialFloor(), FileHandler.readDirection());
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

    private static Elevator loadPreviousState() throws IOException, ClassNotFoundException {
        String filename = Input.readNotEmptyString("Enter the filename to load: ");
        return StateHandler.loadState("./resources/saves/" + filename);
    }
}
