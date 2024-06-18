package test;

import elevator.Building;
import elevator.Elevator;
import elevator.Person;
import elevator.helper.LoadUnloadHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoadUnloadHandlerTest {
    private Elevator elevator;

    @BeforeEach
    void setUp() {
        Building building = new Building(10, -2);
        elevator = new Elevator(building, 5, 0, "up");
    }

    @Test
    void testUnloadPassengers() {
        Person person1 = new Person(0, 5);
        Person person2 = new Person(0, 3);
        elevator.getOnBoard().add(person1);
        elevator.getOnBoard().add(person2);
        elevator.setPersonCount(2);
        elevator.move(); // move to floor 1
        elevator.move(); // move to floor 2
        elevator.move(); // move to floor 3
        LoadUnloadHandler.unloadPassengers(elevator);
        List<Person> onBoard = elevator.getOnBoard();
        assertEquals(1, onBoard.size());
        assertEquals(person1, onBoard.get(0));
    }

    @Test
    void testLoadPassengers() {
        Person person1 = new Person(0, 5);
        elevator.addPersonToWaitingList(person1);
        LoadUnloadHandler.loadPassengers(elevator);
        List<Person> onBoard = elevator.getOnBoard();
        List<Person> waitingList = elevator.getWaitingList();
        assertEquals(1, onBoard.size());
        assertEquals(0, waitingList.size());
        assertEquals(person1, onBoard.get(0));
    }
}
