package test;

import elevator.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    void testPersonInitialization() {
        Person person = new Person(0, 5);
        assertEquals(0, person.getCurrentFloor());
        assertEquals(5, person.getDestinationFloor());
    }

    @Test
    void testSetCurrentFloor() {
        Person person = new Person(0, 5);
        person.setCurrentFloor(1);
        assertEquals(1, person.getCurrentFloor());
    }

    @Test
    void testSetDestinationFloor() {
        Person person = new Person(0, 5);
        person.setDestinationFloor(6);
        assertEquals(6, person.getDestinationFloor());
    }
}
