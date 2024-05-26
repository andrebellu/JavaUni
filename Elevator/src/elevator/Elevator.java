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
        }
    }

    private boolean shouldChangeDirection() {
        int shortestPathFloor;
        int[] personPerFloor = countPersonsPerFloor();
        int[] personDestinationFloor = personDestinationFloor();

        return false;
    }

    private int[] personDestinationFloor() {
        int[] personDestinationFloor = new int[maxFloor - minFloor + 1];
        for (Person person : onBoard) {
            personDestinationFloor[person.getDestinationFloor()]++;
        }

        return personDestinationFloor;
    }

    private int[] countPersonsPerFloor() {
        int[] personsPerFloor = new int[maxFloor - minFloor + 1];
        for (Person person : waitingList) {
            personsPerFloor[person.getCurrentFloor()]++;
        }

        return personsPerFloor;
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
