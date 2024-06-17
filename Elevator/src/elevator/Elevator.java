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
import java.util.Scanner;

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
        while (!waitingList.isEmpty() || !onBoard.isEmpty()) {
            LoadUnloadHandler.unloadPassengers(this);
            LoadUnloadHandler.loadPassengers(this);
            move();
            if (DirectionHandler.shouldChangeDirection(this)) {
                changeDirection();
            }
            printStatus();
            waitEnterUser();
        }

        System.out.println("Simulation ended.");
    }

    /**
     * Waits for the user to press Enter to continue the simulation or press "s" to save the current state and exit.
     *
     * @throws IOException if an I/O error occurs
     */
    private void waitEnterUser() throws IOException {
        System.out.println("~~~~Press Enter to continue or press \"s\" to save the current state and exit...");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equals("s")) {
            String filename = "./resources/saves/";
            filename += Input.readNotEmptyString("Enter the filename to save to: ");
            StateHandler.saveState(this, filename);
            System.out.println("Simulation saved.");
            System.exit(0);
        }
    }

    /**
     * Moves the elevator up or down based on the current direction.
     */
    private void move() {
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
     * Changes the direction of the elevator.
     */
    private void changeDirection() {
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
        for (int i = maxFloor; i >= minFloor; i--) {
            if (i == currentFloor) {
                if (currentFloor <= 9 && currentFloor >= 0) {
                    System.out.println("[" + i + "]   [E]");
                } else if (currentFloor >= 10) {
                    System.out.println("[" + i + "]  [E]");
                } else {
                    System.out.println("[" + i + "]  [E]");
                }
            } else {
                if (i <= 9 && i >= 0) {
                    System.out.println("[" + i + "]   [ ]");
                } else {
                    System.out.println("[" + i + "]  [ ]");
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
     * Returns the number of people on board the elevator.
     *
     * @return the number of people on board the elevator
     */
    public int getPersonCount() {
        return personCount;
    }

    /**
     * Returns whether the emergency stop is activated.
     *
     * @return whether the emergency stop is activated
     */
    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public boolean checkMaxPerson() {
        return personCount < maxPerson;
    }
}
