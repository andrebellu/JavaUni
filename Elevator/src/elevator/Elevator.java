package elevator;

import java.util.*;

public class Elevator {
    private int personCount;
    private int currentFloor;
    private int maxFloor;
    private int minFloor;
    private int maxPerson;
    String direction;
    List<Person> waitingList = new LinkedList<>();
    List<Person> onBoard = new LinkedList<>();

    public Elevator(int maxFloor, int minFloor, int maxPerson, int initialFloor, String direction) {
        this.maxFloor = maxFloor + minFloor;
        this.maxPerson = maxPerson;
        this.minFloor = minFloor;
        this.currentFloor = initialFloor;
        this.personCount = 0;
        this.direction = direction;
    }

    private void waitEnterUser() {
        System.out.println("Press Enter continue...");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.isEmpty() || input.trim().equals(" ")) {
                break;
            }
        }
    }

    public void addPersonToWaitingList(Person person) {
        waitingList.add(person);
    }

    public void simulate() {
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

    private boolean shouldChangeDirection() {
        float[] floorEfficiencyRating = rateFloors();
        System.out.println("Floor efficiency rating: " + Arrays.toString(floorEfficiencyRating));

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
