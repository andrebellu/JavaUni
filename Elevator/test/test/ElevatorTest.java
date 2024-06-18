package test;

import elevator.Building;
import elevator.Elevator;
import elevator.Person;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.Assert.*;

public class ElevatorTest {
    private Building building;
    private Elevator elevator;

    @BeforeEach
    void setUp() {
        building = new Building(10, -2);
        elevator = new Elevator(building, 5, 0, "up");
    }

    @Test
    public void testAddPersonToWaitingList() {
        Person person = new Person(0, 5);
        elevator.addPersonToWaitingList(person);
        List<Person> waitingList = elevator.getWaitingList();
        assertEquals(1, waitingList.size());
        assertEquals(person, waitingList.get(0));
    }

    @Test
    public void testMove() {
        elevator.move();
        assertEquals(1, elevator.getCurrentFloor());
        elevator.move();
        assertEquals(2, elevator.getCurrentFloor());
    }

    @Test
    public void testChangeDirection() {
        elevator.changeDirection();
        assertEquals("down", elevator.getDirection());
        elevator.changeDirection();
        assertEquals("up", elevator.getDirection());
    }

    @Test
    public void testCheckMaxPerson() {
        elevator.setPersonCount(3);
        assertTrue(elevator.checkMaxPerson());
        elevator.setPersonCount(5);
        assertFalse(elevator.checkMaxPerson());
    }
}
