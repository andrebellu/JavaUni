package test;

import elevator.Building;
import elevator.Elevator;
import elevator.Person;
import elevator.helper.DirectionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectionHandlerTest {
    private Elevator elevator;

    @BeforeEach
    void setUp() {
        Building building = new Building(10, -2);
        elevator = new Elevator(building, 5, 0, "up");
    }

    @Test
    void testShouldChangeDirection() {
        Person person1 = new Person(0, 5);
        Person person2 = new Person(0, 3);
        elevator.addPersonToWaitingList(person1);
        elevator.addPersonToWaitingList(person2);
        elevator.move();
        elevator.move();
        assertFalse(DirectionHandler.shouldChangeDirection(elevator));
        elevator.move();
        assertFalse(DirectionHandler.shouldChangeDirection(elevator));
        elevator.move();
        elevator.move();
        assertTrue(DirectionHandler.shouldChangeDirection(elevator));
    }
}
