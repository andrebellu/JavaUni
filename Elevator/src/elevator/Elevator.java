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

    public Elevator(Building building, int maxPerson, int initialFloor, String direction) {
        this.maxFloor = building.maxFloor();
        this.minFloor = building.minFloor();
        this.maxPerson = maxPerson;
        this.currentFloor = initialFloor;
        this.direction = direction;
    }

    public void addPersonToWaitingList(Person person) {
        waitingList.add(person);
    }

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

    private void changeDirection() {
        if (direction.equals("up")) {
            direction = "down";
        } else {
            direction = "up";
        }
    }

    public void printStatus() {
        String direction = this.direction.equals("up") ? "▲" : "▼";
        System.out.println("Floor: " + currentFloor + " " + direction);
        System.out.println("People onboard: " + onBoard.size());
        System.out.println("People waiting: " + waitingList.size());
    }

    public List<Person> getOnBoard() {
        return onBoard;
    }

    public List<Person> getWaitingList() {
        return waitingList;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public void setEmergencyStop(boolean emergencyStop) {
        this.emergencyStop = emergencyStop;
    }

    public boolean checkMaxPerson() {
        return personCount < maxPerson;
    }
}
