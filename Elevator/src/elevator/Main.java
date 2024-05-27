package elevator;

import it.unibs.fp.mylib.Input;

class Main {
    public static void main(String[] args) {

        int numFloors = Input.readInt("Enter the number of floors:", 10, Integer.MAX_VALUE);
        int bottomFloor = Input.readInt("Enter the bottom floor:", Integer.MIN_VALUE, 0);
        int initialFloor = Input.readInt("Enter the initial floor:", bottomFloor, numFloors - 1);
        int maxPerson = Input.readInt("Enter the maximum number of people the elevator can carry:", 1, Integer.MAX_VALUE);
        String direction;
        do {
            direction = Input.readNotEmptyString("Enter the initial direction of the elevator (up or down):").toLowerCase();
        } while (!direction.equals("up") && !direction.equals("down"));


        Elevator elevator = new Elevator(numFloors, bottomFloor, maxPerson, initialFloor, direction);

        do {
            int currentFloor = Input.readInt("Enter the current floor of the person:");
            int destinationFloor = Input.readInt("Enter the destination floor of the person:");
            Person person = new Person(currentFloor, destinationFloor);
            elevator.addPersonToWaitingList(person);
        } while (Input.yesOrNo("Do you want to add another person?"));

        System.out.println("Simulation started.");
        elevator.simulate();
    }
}
