package test;

import elevator.Building;
import elevator.Elevator;
import elevator.Person;
import elevator.helper.LoadUnloadHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for LoadUnloadHandler.
 */
class LoadUnloadHandlerTest {
    private Elevator elevator;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        Building building = new Building(10, -2);
        elevator = new Elevator(building, 5, 0, "up");
    }

    /**
     * Tests the unloading of passengers from the elevator.
     */
    @Test
    void testUnloadPassengers() {
        Person person1 = new Person(0, 5);
        Person person2 = new Person(0, 3);
        elevator.getOnBoard().add(person1);
        elevator.getOnBoard().add(person2);
        elevator.setPersonCount(2);
        elevator.move();
        elevator.move();
        elevator.move();
        LoadUnloadHandler.unloadPassengers(elevator);
        List<Person> onBoard = elevator.getOnBoard();
        assertEquals(1, onBoard.size());
        assertEquals(person1, onBoard.get(0));
    }

    /**
     * Tests the loading of passengers into the elevator.
     */
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

    /**
     * Tests loading passengers into the elevator when it is at full capacity.
     */
    @Test
    void testLoadPassengersAtMaxCapacity() {
        for (int i = 0; i < 10; i++) {
            elevator.addPersonToWaitingList(new Person(0, 5));
        }
        LoadUnloadHandler.loadPassengers(elevator);
        assertEquals(5, elevator.getOnBoard().size());
        assertEquals(5, elevator.getWaitingList().size());
    }

    /**
     * Tests the unloading and loading of passengers in the elevator.
     */
    @Test
    void testUnloadAndLoadPassengers() {
        Person person1 = new Person(0, 5);
        Person person2 = new Person(0, 3);
        Person person3 = new Person(0, 1);
        elevator.addPersonToWaitingList(person1);
        elevator.addPersonToWaitingList(person2);
        elevator.addPersonToWaitingList(person3);
        LoadUnloadHandler.loadPassengers(elevator);
        elevator.move();
        LoadUnloadHandler.unloadPassengers(elevator);
        List<Person> onBoard = elevator.getOnBoard();
        List<Person> waitingList = elevator.getWaitingList();
        assertEquals(2, onBoard.size());
        assertEquals(0, waitingList.size());
    }
}
