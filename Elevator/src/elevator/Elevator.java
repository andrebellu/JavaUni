package elevator;

import it.unibs.fp.mylib.Input;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.IntStream;

/**
 * The {@code Elevator} class represents an elevator in a building.
 * It simulates the behavior of an elevator, managing the loading and unloading
 * of passengers, moving between floors, and handling direction changes.
 */
public class Elevator implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int personCount;
    private int currentFloor;
    private final int maxFloor;
    private final int minFloor;
    private final int maxPerson;
    private String direction;
    private final List<Person> waitingList = new LinkedList<>();
    private final List<Person> onBoard = new LinkedList<>();

    /**
     * Constructs an {@code Elevator} object with the specified building, maximum person capacity,
     * initial floor, and initial direction.
     *
     * @param building      the building in which the elevator operates
     * @param maxPerson     the maximum number of people the elevator can carry
     * @param initialFloor  the initial floor of the elevator
     * @param direction     the initial direction of the elevator ("up" or "down")
     */
    public Elevator(Building building, int maxPerson, int initialFloor, String direction) {
        this.maxFloor = building.maxFloor();
        this.minFloor = building.minFloor();
        this.maxPerson = maxPerson;
        this.currentFloor = initialFloor;
        this.direction = direction;
    }

    /**
     * Adds a person to the waiting list.
     *
     * @param person the person to be added to the waiting list
     */
    public void addPersonToWaitingList(Person person) {
        waitingList.add(person);
    }

    /**
     * Simulates the elevator operation by repeatedly loading and unloading passengers,
     * moving between floors, and changing direction if needed.
     *
     * @throws IOException if an I/O error occurs
     */
    public void simulate() throws IOException {
        while (!waitingList.isEmpty() || !onBoard.isEmpty()) {
            unloadPassengers();
            loadPassengers();
            move();
            if (shouldChangeDirection()) {
                changeDirection();
            }
            printStatus();
            waitEnterUser();
        }

        System.out.println("Simulation ended.");
    }

    /**
     * Waits for user input to either continue the simulation or save the current state.
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
     * Determines if the elevator should change direction based on floor efficiency ratings.
     *
     * @return {@code true} if the elevator should change direction, {@code false} otherwise
     */
    private boolean shouldChangeDirection() {
        Map<Integer, Float> floorEfficiencyRating = rateFloors();

        float maxRating = Float.NEGATIVE_INFINITY;
        int targetFloor = currentFloor;

        for (Map.Entry<Integer, Float> entry : floorEfficiencyRating.entrySet()) {
            if (entry.getValue() > maxRating) {
                maxRating = entry.getValue();
                targetFloor = entry.getKey();
            }
        }

        return currentFloor > targetFloor && direction.equals("up") ||
                currentFloor < targetFloor && direction.equals("down");
    }

    /**
     * Rates the floors based on the number of people waiting and onboard, as well as distance penalties.
     *
     * @return an array of floor efficiency ratings
     */
    private Map<Integer, Float> rateFloors() {
        Map<Integer, Float> floorMap = new TreeMap<>();

        final float waitingWeight = 1.0F;
        final float onboardWeight = 1.5F;
        final float distancePenalty = 0.5F;

        IntStream.rangeClosed(minFloor, maxFloor).forEach(floor -> {
            float rating = 0F;
            int distance = Math.abs(currentFloor - floor);

            long waitingCount = waitingList.stream()
                    .filter(person -> person.getCurrentFloor() == floor)
                    .count();

            long onboardCount = onBoard.stream()
                    .filter(person -> person.getDestinationFloor() == floor)
                    .count();

            rating += waitingCount * waitingWeight;
            rating += onboardCount * onboardWeight;
            rating -= distance * distancePenalty;

            floorMap.put(floor, rating);
        });

        return floorMap;
    }

    /**
     * Moves the elevator one floor in the current direction.
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
     * Unloads passengers at the current floor.
     */
    private void unloadPassengers() {
        Iterator<Person> iterator = onBoard.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getDestinationFloor() == currentFloor) {
                iterator.remove();
                personCount--;
            }
        }
    }

    /**
     * Loads passengers at the current floor, if the elevator is not full.
     */
    private void loadPassengers() {
        Iterator<Person> iterator = waitingList.iterator();
        while (iterator.hasNext() && checkMaxPerson()) {
            Person person = iterator.next();
            if (person.getCurrentFloor() == currentFloor) {
                if (personCount < maxPerson) {
                    onBoard.add(person);
                    iterator.remove();
                    personCount++;
                }
            }
        }
    }

    /**
     * Checks if the elevator is below the maximum person capacity.
     *
     * @return {@code true} if the elevator is below the maximum capacity, {@code false} otherwise
     */
    private boolean checkMaxPerson() {
        return personCount < maxPerson;
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
     * Prints the current status of the elevator.
     */
    public void printStatus() {
        String direction = this.direction.equals("up") ? "▲" : "▼";
        System.out.println("Floor: " + currentFloor + " " + direction);
        System.out.println("People onboard: " + onBoard.size());
        System.out.println("People waiting: " + waitingList.size());
    }

    /**
     * Returns the list of people onboard the elevator.
     *
     * @return the list of people onboard
     */
    public List<Person> getOnBoard() {
        return onBoard;
    }

    /**
     * Returns the list of people waiting for the elevator.
     *
     * @return the list of people waiting
     */
    public List<Person> getWaitingList() {
        return waitingList;
    }

    /**
     * Returns the current direction of the elevator.
     *
     * @return the current direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the elevator.
     *
     * @param direction the new direction ("up" or "down")
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Returns the maximum number of people the elevator can carry.
     *
     * @return the maximum number of people
     */
    public int getMaxPerson() {
        return maxPerson;
    }

    /**
     * Returns the minimum floor the elevator can reach.
     *
     * @return the minimum floor
     */
    public int getMinFloor() {
        return minFloor;
    }

    /**
     * Returns the maximum floor the elevator can reach.
     *
     * @return the maximum floor
     */
    public int getMaxFloor() {
        return maxFloor;
    }

    /**
     * Returns the current floor of the elevator.
     *
     * @return the current floor
     */
    public int getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Sets the current floor of the elevator.
     *
     * @param currentFloor the new current floor
     */
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    /**
     * Returns the current number of people in the elevator.
     *
     * @return the current number of people
     */
    public int getPersonCount() {
        return personCount;
    }

    /**
     * Sets the current number of people in the elevator.
     *
     * @param personCount the new number of people
     */
    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }
}
