package elevator;

import it.unibs.fp.mylib.Input;

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

    public Elevator(int maxFloor, int minFloor, int maxPerson, int initialFloor) {
        this.maxFloor = maxFloor;
        this.maxPerson = maxPerson;
        this.currentFloor = initialFloor;
        this.personCount = 0;
        this.direction = "up";
    }

    private void waitEnterUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to continue...");
        scanner.nextLine();
    }

    public void addPersonToWaitingList(Person person) {
        waitingList.add(person);
    }

    public void simulate() {
        while (!waitingList.isEmpty() || !onBoard.isEmpty()) {
            move();
            unloadPassengers();
            loadPassengers();
            printStatus();
            if (shouldChangeDirection()) {
                changeDirection();
            }
            waitEnterUser();

            if (waitingList.isEmpty() && onBoard.isEmpty() && Input.yesOrNo("Do you want to add another person?")) {
                do {
                    int currentFloor = Input.readInt("Enter the current floor of the person:");
                    int destinationFloor = Input.readInt("Enter the destination floor of the person:");
                    Person person = new Person(currentFloor, destinationFloor);
                    addPersonToWaitingList(person);
                } while (Input.yesOrNo("Do you want to add another person?"));
            }
        }
    }

    private boolean shouldChangeDirection() {
        int efficientPath;

        int[] floorEfficiencyRating = rateFloors();

        System.out.println("Floor efficiency rating: " + Arrays.toString(floorEfficiencyRating));

        for (int i = 0; i < maxFloor; i++) {
            if (floorEfficiencyRating[i] > 0) {
                efficientPath = i;
                if (efficientPath > currentFloor) {
                    return !direction.equals("up");
                } else {
                    return !direction.equals("down");
                }
            }
        }

        return false;
    }

    private int[] rateFloors() {
        int currentFloor = getCurrentFloor();
        int[] floorEfficiencyRating = new int[maxFloor];
        int points = maxFloor;
        for (int i = 0; i < maxFloor; i++) {
            floorEfficiencyRating[i] = 0;
        }
        for (Person person : waitingList) {
            floorEfficiencyRating[person.getCurrentFloor()]++;
        }
        for (Person person : onBoard) {
            floorEfficiencyRating[person.getDestinationFloor()]++;
        }

        for (int i = 0; i < maxFloor; i++) {
            if (i == currentFloor || floorEfficiencyRating[i] == 0) {
                floorEfficiencyRating[i] = 0;
            } else {
                floorEfficiencyRating[i] += points - (currentFloor - i);
                points--;
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
        while (iterator.hasNext()) {
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
        System.out.println("Person count: " + personCount);
        System.out.println("Onboard persons: " + onBoard.size());
        System.out.println("Waiting list persons: " + waitingList.size());
        System.out.println("-----");
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
