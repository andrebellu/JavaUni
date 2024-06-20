package elevator;

import elevator.helper.DirectionHandler;
import elevator.helper.LoadUnloadHandler;
import elevator.helper.StateHandler;
import it.unibs.fp.mylib.Input;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The {@code Elevator} class represents an elevator in the simulation.
 * Implements {@code Serializable} to allow saving and loading the state of the elevator.
 */
public class Elevator implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int maxFloor;
    private final int minFloor;
    private final int maxPerson;
    private final List<Person> waitingList = new LinkedList<>();
    private final List<Person> onBoard = new LinkedList<>();
    private int personCount;
    private int currentFloor;
    private String direction;
    private boolean emergencyStop = false;

    /**
     * Constructs an {@code Elevator} object with the specified building, maximum number of people, initial floor, and direction.
     *
     * @param building     the building object
     * @param maxPerson    the maximum number of people the elevator can hold
     * @param initialFloor the initial floor of the elevator
     * @param direction    the initial direction of the elevator
     */
    public Elevator(Building building, int maxPerson, int initialFloor, String direction) {
        this.maxFloor = building.maxFloor();
        this.minFloor = building.minFloor();
        this.maxPerson = maxPerson;
        this.currentFloor = initialFloor;
        this.direction = direction;
    }

    /**
     * Adds a person to the elevator.
     *
     * @param person the person to add to the elevator
     */
    public void addPersonToWaitingList(Person person) {
        waitingList.add(person);
    }

    /**
     * Adds a person to the elevator.
     *
     * @throws IOException if an I/O error occurs
     */
    public void simulate() throws IOException {
        while (continueSimulation()) {
            if (!emergencyStop) {
                performElevatorActions();
            } else {
                LoadUnloadHandler.unloadPassengersEmergency(this);
            }
            printStatus();
            waitEnterUser();
        }
        System.out.println("Simulation ended.");
    }

    /**
     * Waits for the user to press Enter to continue the simulation, press "s" to save the current state and exit, press "e" to activate/deactivate emergency stop, or press "a" to add a person.
     *
     * @throws IOException if an I/O error occurs
     */
    private void waitEnterUser() throws IOException {
        System.out.println("~~~~Press:\n• 'Enter' to continue\n• 's' to save and exit\n• 'e' to " + (emergencyStop ? "deactivate" : "activate") + " emergency stop\n• 'a' to add a person");
        String input = Input.readString("Enter your choice: ").toLowerCase();

        switch (input) {
            case "s" -> {
                String filename = "./resources/saves/";
                filename += Input.readNotEmptyString("Enter the filename to save to: ");
                StateHandler.saveState(this, filename);
                System.out.println("Simulation saved.");
                System.exit(0);
            }
            case "e" -> {
                setEmergencyStop();
                System.out.println("Emergency stop " + (emergencyStop ? "activated" : "deactivated"));
            }
            case "a" -> {
                int currentFloor = Input.readInt("Enter the current floor of the person: ");
                int destinationFloor = Input.readInt("Enter the destination floor of the person: ");
                Person person = new Person(currentFloor, destinationFloor);
                addPersonToWaitingList(person);
            }
        }
    }

    /**
     * Moves the elevator up or down based on the current direction.
     */
    public void move() {
        if (emergencyStop) {
            System.out.println("Emergency stop activated. Elevator is not moving.");
            return;
        }

        if (direction.equals("up")) {
            currentFloor++;
            if (currentFloor == maxFloor) {
                changeDirection();
            }
        } else {
            currentFloor--;
            if (currentFloor == minFloor) {
                changeDirection();
            }
        }
    }

    /**
     * Checks if the simulation should continue.
     *
     * @return true if the simulation should continue, false otherwise
     */
    private boolean continueSimulation() {
        return !waitingList.isEmpty() || !onBoard.isEmpty();
    }

    /**
     * Performs the actions of the elevator.
     */
    private void performElevatorActions() {
        LoadUnloadHandler.unloadPassengers(this);
        LoadUnloadHandler.loadPassengers(this);
        if (DirectionHandler.shouldChangeDirection(this)) {
            changeDirection();
        }
        move();
    }

    /**
     * Changes the direction of the elevator.
     */
    public void changeDirection() {
        if (direction.equals("up")) {
            direction = "down";
        } else {
            direction = "up";
        }
    }

    /**
     * Prints the status of the elevator.
     */
    public void printStatus() {
        String direction = this.direction.equals("up") ? "▲" : "▼";
        System.out.println("Floor: " + currentFloor + " " + direction);
        System.out.println("People onboard: " + onBoard.size());
        System.out.println("People waiting: " + waitingList.size());
        printBuildingRepresentation();
    }

    /**
     * Prints a representation of the building with the elevator position.
     */
    public void printBuildingRepresentation() {
        // Group people by their current floor
        Map<Integer, Long> peopleOnFloors = waitingList.stream()
                .collect(Collectors.groupingBy(Person::getCurrentFloor, Collectors.counting()));

        for (int i = maxFloor; i >= minFloor; i--) {
            String peopleDots = ".".repeat(Math.toIntExact(peopleOnFloors.getOrDefault(i, 0L)));

            if (i == currentFloor) {
                if (currentFloor <= 9 && currentFloor >= 0) {
                    System.out.println("[" + i + "]   [E] " + peopleDots);
                } else if (currentFloor >= 10) {
                    System.out.println("[" + i + "]  [E] " + peopleDots);
                } else {
                    System.out.println("[" + i + "]  [E] " + peopleDots);
                }
            } else {
                if (i <= 9 && i >= 0) {
                    System.out.println("[" + i + "]   [ ] " + peopleDots);
                } else {
                    System.out.println("[" + i + "]  [ ] " + peopleDots);
                }
            }
        }
    }

    /**
     * Returns the list of people on board the elevator.
     *
     * @return the list of people on board the elevator
     */
    public List<Person> getOnBoard() {
        return onBoard;
    }

    /**
     * Returns the list of people waiting for the elevator.
     *
     * @return the list of people waiting for the elevator
     */
    public List<Person> getWaitingList() {
        return waitingList;
    }

    /**
     * Returns the direction of the elevator.
     *
     * @return the direction of the elevator
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Returns the maximum number of people the elevator can hold.
     *
     * @return the maximum number of people the elevator can hold
     */
    public int getMaxPerson() {
        return maxPerson;
    }

    /**
     * Returns the minimum floor of the building.
     *
     * @return the minimum floor of the building
     */
    public int getMinFloor() {
        return minFloor;
    }

    /**
     * Returns the maximum floor of the building.
     *
     * @return the maximum floor of the building
     */
    public int getMaxFloor() {
        return maxFloor;
    }

    /**
     * Returns the current floor of the elevator.
     *
     * @return the current floor of the elevator
     */
    public int getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Checks if the elevator can take more people
     *
     * @return true if the elevator can take more people, false otherwise
     */
    public boolean checkMaxPerson() {
        return getOnBoard().size() < maxPerson;
    }

    public void setEmergencyStop() {
        emergencyStop = !emergencyStop;
    }
}
