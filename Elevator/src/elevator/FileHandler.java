package elevator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {
    static final String OPTIONS = System.getProperty("os.name").toLowerCase().contains("win") ? "resources\\options.txt" : "resources/options.txt";
    static final String INPUT = System.getProperty("os.name").toLowerCase().contains("win") ? "resources\\input.txt" : "resources/input.txt";

    public static Elevator readElevatorSettings() throws IOException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(OPTIONS));
        String line;
        int numFloors = 0;
        int bottomFloor = 0;
        int initialFloor = 0;
        int maxPerson = 0;
        String direction = "";

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("number of floors:")) {
                numFloors = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("bottom floor:")) {
                bottomFloor = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("initial floor:")) {
                initialFloor = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("max people:")) {
                maxPerson = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("direction:")) {
                direction = line.split(":")[1].trim().toLowerCase();
                break;
            }
        }

        reader.close();
        return new Elevator(numFloors, bottomFloor, maxPerson, initialFloor, direction);
    }

    public static void readPeopleData(Elevator elevator) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUT));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("--")) {
                continue;
            }

            String[] parts = line.split("\\s+");
            if (parts.length == 2) {
                int currentFloor = Integer.parseInt(parts[0]);
                int destinationFloor = Integer.parseInt(parts[1]);
                Person person = new Person(currentFloor, destinationFloor);
                elevator.addPersonToWaitingList(person);
            }
        }

        reader.close();
    }
}