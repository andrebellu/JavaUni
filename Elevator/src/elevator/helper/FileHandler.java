package elevator.helper;

import elevator.Building;
import elevator.Elevator;
import elevator.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The {@code FileHandler} class reads input data from files for the initialization of the elevator system.
 */
public class FileHandler {
    // static final String OPTIONS = System.getProperty("os.name").toLowerCase().contains("win") ? "resources\\options.txt" : "resources/options.txt";
    // static final String INPUT = System.getProperty("os.name").toLowerCase().contains("win") ? "resources\\input.txt" : "resources/input.txt";
    static final String OPTIONS = "resources/sim_settings/options.txt";
    static final String INPUT = "resources/sim_settings/input.txt";

    /**
     * Reads the building settings from the options file.
     *
     * @return a {@code Building} object with the number of floors and the bottom floor
     * @throws IOException if an I/O error occurs
     */
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
                if (numFloors < 10) {
                    throw new IllegalArgumentException("Number of floors must be at least 10.");
                }
            } else if (line.startsWith("bottom floor:")) {
                bottomFloor = Integer.parseInt(line.split(":")[1].trim());
                if (bottomFloor >= 0) {
                    throw new IllegalArgumentException("Bottom floor must be negative or zero.");
                }
            }
        }

        numFloors = numFloors + bottomFloor - 1;

        reader.close();
        return new Building(numFloors, bottomFloor);
    }

    /**
     * Reads the maximum number of people the elevator can carry from the options file.
     *
     * @return the maximum number of people the elevator can carry
     * @throws IOException if an I/O error occurs
     */
    public static int readMaxPeople() throws IOException {
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

    /**
     * Reads the initial floor of the elevator from the options file.
     *
     * @return the initial floor of the elevator
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Reads the initial direction of the elevator from the options file.
     *
     * @return the initial direction of the elevator ("up" or "down")
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Reads the people data from the input file and adds them to the waiting list of the elevator.
     *
     * @param elevator the elevator object
     * @throws IOException if an I/O error occurs
     */
    public static void readPeopleData(Elevator elevator) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(INPUT));
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("--")) {
                continue;
            }

            String[] parts = line.split(" ");
            if (parts.length == 2) {
                int currentFloor = Integer.parseInt(parts[0]);
                int destinationFloor = Integer.parseInt(parts[1]);

                if (currentFloor == destinationFloor) {
                    System.out.println("\n‼️Error: current floor and destination floor cannot be the same, check the input file. Person skipped.\n");
                    continue;
                }

                if (currentFloor < elevator.getMinFloor() || currentFloor > elevator.getMaxFloor() ||
                        destinationFloor < elevator.getMinFloor() || destinationFloor > elevator.getMaxFloor()) {
                    System.out.println("\n‼️Error: floor number out of range, check the input file. Person skipped.\n");
                    continue;
                }

                Person person = new Person(currentFloor, destinationFloor);

                elevator.addPersonToWaitingList(person);
            }
        }

        reader.close();
    }
}
