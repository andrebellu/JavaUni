package elevator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Elevator elevator = FileHandler.readElevatorSettings();
        FileHandler.readPeopleData(elevator);
        elevator.simulate();
    }
}