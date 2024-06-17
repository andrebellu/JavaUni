package elevator.helper;

import elevator.Elevator;
import elevator.Person;

import java.util.Iterator;

public class LoadUnloadHandler {
    public static void unloadPassengers(Elevator elevator) {
        Iterator<Person> iterator = elevator.getOnBoard().iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getDestinationFloor() == elevator.getCurrentFloor()) {
                iterator.remove();
                elevator.setPersonCount(elevator.getPersonCount() - 1);
            }
        }
    }

    public static void loadPassengers(Elevator elevator) {
        Iterator<Person> iterator = elevator.getWaitingList().iterator();
        while (iterator.hasNext() && elevator.checkMaxPerson()) {
            Person person = iterator.next();
            if (person.getCurrentFloor() == elevator.getCurrentFloor()) {
                if (elevator.getPersonCount() < elevator.getMaxPerson()) {
                    elevator.getOnBoard().add(person);
                    iterator.remove();
                    elevator.setPersonCount(elevator.getPersonCount() + 1);
                }
            }
        }
    }
}
