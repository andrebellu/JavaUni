package elevator;

import it.unibs.fp.mylib.Input;

class Main {
    public static void main(String[] args) {

        int numFloors = Input.readInt("Enter the number of floors:", 10, Integer.MAX_VALUE);
        int bottomFloor = Input.readInt("Enter the bottom floor:", Integer.MIN_VALUE, 0);

        Elevator elevator = new Elevator(numFloors, bottomFloor, 10, 0);

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