package elevator;

import java.util.*;

public class Elevator {
    private int personCount;
    private int currentFloor;
    private int maxFloor;
    private int minFloor;
    private int maxPerson;
    String direction;
    List<Person> commands = new LinkedList<>();
    List<Person> waitingList = new LinkedList<>();
    List<Person> onBoard = new LinkedList<>();

    public Elevator(int maxFloor, int minFloor, int maxPerson, int initialFloor) {
        this.maxFloor = maxFloor;
        this.minFloor = minFloor;
        this.maxPerson = maxPerson;
        this.currentFloor = initialFloor;
        this.personCount = 0;
        this.direction = "up";
    }

    public void addPersonToWaitingList(Person person) {
        waitingList.add(person);
    }

    public void simulate() {
    }

    private void move() {
    }

    private void unloadPassengers() {
    }

    private void loadPassengers() {
    }

    private void changeDirection() {

    }

    public void printStatus() {
    }

    // getter and setter methods

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public void setMinFloor(int minFloor) {
        this.minFloor = minFloor;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }
}
