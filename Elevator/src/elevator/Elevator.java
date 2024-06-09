package elevator;

import it.unibs.fp.mylib.Input;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Elevator implements Serializable {

    private int personCount;
    private int currentFloor;
    private int maxFloor;
    private int minFloor;
    private int maxPerson;
    private String direction;
    private List<Person> waitingList = new LinkedList<>();
    private List<Person> onBoard = new LinkedList<>();

    public Elevator(Building building, int maxPerson, int initialFloor, String direction) {
        this.maxFloor = building.getMaxFloor();
        this.minFloor = building.getMinFloor();
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
            printStatus();
            if (shouldChangeDirection()) {
                changeDirection();
            }
            waitEnterUser();
        }

        System.out.println("Simulation ended.");
    }

    private void waitEnterUser() throws IOException {
        System.out.println("Press Enter to continue or press \"s\" to save the current state and exit...");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("s")) {
            String filename = "./resources/saves/";
            filename += Input.readNotEmptyString("Enter the filename to save to: ");
            StateHandler.saveState(this, filename);
            System.out.println("Simulation saved.");
            System.exit(0);
        } else {
            System.out.println("Continuing simulation...");
        }
    }

    private boolean shouldChangeDirection() {
        float[] floorEfficiencyRating = rateFloors();
        System.out.println(Arrays.toString(floorEfficiencyRating));

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
        System.out.println("Current floor: " + currentFloor);
        System.out.println("Direction: " + direction);
        System.out.println("Onboard people: " + onBoard.size());
        System.out.println("Waiting list people: " + waitingList.size());
        System.out.println("██████████████████████Press Enter to continue...██████████████████████");
    }
}
