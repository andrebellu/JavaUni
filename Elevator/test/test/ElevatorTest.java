package test;

import elevator.Building;
import elevator.Elevator;
import elevator.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Elevator class.
 */
class ElevatorTest {
    private Building building;
    private Elevator elevator;

    /**
     * Sets up the test environment before each test method.
     * Creates a new building and elevator for each test.
     */
    @BeforeEach
    void setUp() {
        building = new Building(10, -2);
        elevator = new Elevator(building, 5, 0, "up");
    }

    /**
     * Tests the addPersonToWaitingList method.
     * Verifies that a person is correctly added to the waiting list.
     */
    @Test
    public void testAddPersonToWaitingList() {
        Person person = new Person(0, 5);
        elevator.addPersonToWaitingList(person);
        List<Person> waitingList = elevator.getWaitingList();
        assertEquals(1, waitingList.size());
        assertEquals(person, waitingList.get(0));  // Updated to get(0) for List
    }

    /**
     * Tests the move method when the elevator moves up.
     * Verifies that the elevator correctly moves up by one floor each time.
     */
    @Test
    public void testMoveUp() {
        elevator.move();
        assertEquals(1, elevator.getCurrentFloor());
        elevator.move();
        assertEquals(2, elevator.getCurrentFloor());
    }

    /**
     * Tests the move method when the elevator moves down.
     * Verifies that the elevator correctly moves down by one floor each time.
     */
    @Test
    public void testMoveDown() {
        elevator.changeDirection();
        elevator.move();
        assertEquals(-1, elevator.getCurrentFloor());
        elevator.move();
        assertEquals(-2, elevator.getCurrentFloor());
    }

    /**
     * Tests the changeDirection method.
     * Verifies that the elevator correctly changes direction.
     */
    @Test
    public void testChangeDirection() {
        elevator.changeDirection();
        assertEquals("down", elevator.getDirection());
        elevator.changeDirection();
        assertEquals("up", elevator.getDirection());
    }

    /**
     * Tests the checkMaxPerson method.
     * Verifies that the method correctly identifies when the elevator is at or below maximum capacity.
     */
    @Test
    public void testCheckMaxPerson() {
        elevator.setPersonCount(3);
        assertTrue(elevator.checkMaxPerson());
        elevator.setPersonCount(5);
        assertFalse(elevator.checkMaxPerson());
    }

    /**
     * Tests the printStatus method.
     * This test primarily prints output and is intended for manual inspection.
     */
    @Test
    public void testPrintStatus() {
        elevator.printStatus();
    }

    /**
     * Tests the emergency stop functionality.
     * Verifies that the elevator does not move when emergency stop is activated.
     */
    @Test
    public void testEmergencyStop() {
        elevator.setEmergencyStop();
        elevator.move();
        elevator.move();
        assertEquals(0, elevator.getCurrentFloor());
    }

    /**
     * Tests the behavior of the elevator at edge case floors.
     * Verifies that the elevator correctly handles moving from the lowest and highest floors.
     */
    @Test
    public void testEdgeCaseFloors() {
        elevator = new Elevator(new Building(10, -2), 5, -2, "up");
        elevator.move();
        assertEquals(-1, elevator.getCurrentFloor());
        elevator = new Elevator(new Building(10, -2), 5, 10, "down");
        elevator.move();
        assertEquals(9, elevator.getCurrentFloor());
    }
}
