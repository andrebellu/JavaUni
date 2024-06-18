package test;

import elevator.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    @Test
    void testPerson() {
        Person person = new Person(0, 5);
        assertEquals(0, person.getCurrentFloor());
        assertEquals(5, person.getDestinationFloor());

        person.setCurrentFloor(1);
        person.setDestinationFloor(6);
        assertEquals(1, person.getCurrentFloor());
        assertEquals(6, person.getDestinationFloor());
    }
}
