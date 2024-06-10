package elevator;

import it.unibs.fp.mylib.Input;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

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

    private boolean shouldChangeDirection() {
        float[] floorEfficiencyRating = rateFloors();
        // System.out.println(Arrays.toString(floorEfficiencyRating));

        float maxRating = 0F;
        int targetFloor = currentFloor;

        for (int i = 0; i < floorEfficiencyRating.length; i++) {
            if (floorEfficiencyRating[i] > maxRating) {
                maxRating = floorEfficiencyRating[i];
                targetFloor = i;
            }
        }

        if (targetFloor > currentFloor && !direction.equals("up")) {
            return true;
        } else return targetFloor < currentFloor && !direction.equals("down");
    }

    private float[] rateFloors() {
        int totalFloors = maxFloor + Math.abs(minFloor) + 1;
        float[] floorEfficiencyRating = new float[totalFloors];
        Arrays.fill(floorEfficiencyRating, 0);

        float waitingWeight = 1.0F;
        float onboardWeight = 1.5F;
        float distancePenalty = 0.5F;

        for (Person person : waitingList) {
            int floorIndex = person.getCurrentFloor();
            floorEfficiencyRating[floorIndex] += waitingWeight;
        }

        for (Person person : onBoard) {
            int floorIndex = person.getDestinationFloor();
            floorEfficiencyRating[floorIndex] += onboardWeight;
        }

        for (int i = 0; i < totalFloors; i++) {
            if (floorEfficiencyRating[i] > 0) {
                int distance = Math.abs(currentFloor - i);
                floorEfficiencyRating[i] -= distance * distancePenalty;

                if ((direction.equals("up") && i > currentFloor) ||
                        (direction.equals("down") && i < currentFloor)) {
                    floorEfficiencyRating[i] += distancePenalty;
                }
            }
        }

        return floorEfficiencyRating;
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

    private boolean checkMaxPerson() {
        return personCount < maxPerson;
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
        System.out.println("▉" + direction);
        System.out.println("Current floor: " + currentFloor);
        System.out.println("Onboard people: " + onBoard.size());
        System.out.println("Waiting list people: " + waitingList.size());
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
}
