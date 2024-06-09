package elevator;

import java.io.Serializable;

public class Person implements Serializable {
    private int currentFloor;
    private int destinationFloor;

    public Person(int currentFloor, int destinationFloor) {
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}
