package test;

import elevator.Building;
import elevator.Elevator;
import elevator.Person;
import elevator.helper.DirectionHandler;
import elevator.helper.LoadUnloadHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the DirectionHandler class.
 */
class DirectionHandlerTest {
    private Elevator elevator;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        Building building = new Building(10, -2);
        elevator = new Elevator(building, 5, 0, "down");
    }

    /**
     * Tests if the elevator should change direction when there are passengers if the elevator is going up and there a person closer in the opposite direction.
     */
    @Test
    void testShouldChangeDirectionWithPassengers() {
        Person person1 = new Person(1, 5);
        Person person2 = new Person(0, -1);
        elevator.addPersonToWaitingList(person1);
        elevator.addPersonToWaitingList(person2);
        LoadUnloadHandler.loadPassengers(elevator);
        elevator.move();
        elevator.move();
        assertFalse(DirectionHandler.shouldChangeDirection(elevator));
    }

    /**
     * Tests if the elevator should change direction when it is at the top floor.
     */
    @Test
    void testShouldChangeDirectionAtTopFloor() {
        elevator = new Elevator(new Building(10, -2), 5, 10, "up");
        elevator.move();
        assertEquals("up", elevator.getDirection());
    }

    /**
     * Tests if the elevator should change direction when it is at the bottom floor.
     */
    @Test
    void testShouldChangeDirectionAtBottomFloor() {
        elevator = new Elevator(new Building(10, -2), 5, -2, "down");
        elevator.move();
        assertEquals("down", elevator.getDirection());
    }
}
