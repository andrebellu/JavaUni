package elevator;

import java.io.Serial;
import java.io.Serializable;

/**
 * The {@code Person} class represents a person in the elevator simulation.
 */
public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int currentFloor;
    private int destinationFloor;

    /**
     * Constructs a {@code Person} object with the specified current floor and destination floor.
     * @param currentFloor the current floor of the person
     * @param destinationFloor the destination floor of the person
     */
    public Person(int currentFloor, int destinationFloor) {
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    /**
     * Returns the current floor of the person.
     * @return the current floor of the person
     */
    public int getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Sets the current floor of the person.
     * @param currentFloor the current floor of the person
     */
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    /**
     * Sets the destination floor of the person.
     * @param destinationFloor the destination floor of the person
     */
    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    /**
     * Returns the destination floor of the person.
     * @return the destination floor of the person
     */
    public int getDestinationFloor() {
        return destinationFloor;
    }
}
