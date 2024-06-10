package elevator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {
    // static final String OPTIONS = System.getProperty("os.name").toLowerCase().contains("win") ? "resources\\options.txt" : "resources/options.txt";
    // static final String INPUT = System.getProperty("os.name").toLowerCase().contains("win") ? "resources\\input.txt" : "resources/input.txt";
    static final String OPTIONS = "resources/options.txt";
    static final String INPUT = "resources/input.txt";

    public static Building readBuildingSettings() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(OPTIONS));
        String line;
        int numFloors = 0;
        int bottomFloor = 0;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("number of floors:")) {
                numFloors = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("bottom floor:")) {
                bottomFloor = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        reader.close();
        return new Building(numFloors, bottomFloor);
    }

    public static int readMaxPersons() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(OPTIONS));
        String line;
        int maxPerson = 0;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("max people:")) {
                maxPerson = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        reader.close();
        return maxPerson;
    }

    public static int readInitialFloor() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(OPTIONS));
        String line;
        int initialFloor = 0;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("initial floor:")) {
                initialFloor = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        reader.close();
        return initialFloor;
    }

    public static String readDirection() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(OPTIONS));
        String line;
        String direction = "up";

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("initial direction:")) {
                direction = line.split(":")[1].trim();
            }
        }

        reader.close();
        return direction;
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

                if (currentFloor == destinationFloor) {
                    System.out.println("\n‼️Error: current floor and destination floor cannot be the same, check the input file. Person skipped.\n");
                    continue;
                }

                Person person = new Person(currentFloor, destinationFloor);

                elevator.addPersonToWaitingList(person);
            }
        }

        reader.close();
    }
}
