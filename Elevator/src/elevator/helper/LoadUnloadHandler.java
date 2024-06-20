package elevator.helper;

import elevator.Elevator;
import elevator.Person;

import java.util.Iterator;

/**
 * A utility class that handles loading and unloading of passengers in an elevator.
 */
public class LoadUnloadHandler {

    /**
     * Unloads passengers from the elevator who have reached their destination floor.
     *
     * @param elevator the elevator from which passengers are to be unloaded.
     */
    public static void unloadPassengers(Elevator elevator) {
        elevator.getOnBoard().removeIf(person -> person.getDestinationFloor() == elevator.getCurrentFloor());
    }

    /**
     * Loads passengers into the elevator from the waiting list, provided the elevator is not at full capacity.
     *
     * @param elevator the elevator into which passengers are to be loaded.
     */
    public static void loadPassengers(Elevator elevator) {
        Iterator<Person> iterator = elevator.getWaitingList().iterator();

        while (iterator.hasNext() && elevator.checkMaxPerson()) {
            Person person = iterator.next();
            if (person.getCurrentFloor() == elevator.getCurrentFloor()) {
                if (elevator.getOnBoard().size() < elevator.getMaxPerson()) {
                    elevator.getOnBoard().add(person);
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Unloads all passengers from the elevator in case of an emergency, placing them back on the waiting list with their current floor set as the elevator's current floor.
     *
     * @param elevator the elevator from which passengers are to be unloaded during an emergency.
     */
    public static void unloadPassengersEmergency(Elevator elevator) {
        for (Person person : elevator.getOnBoard()) {
            person.setCurrentFloor(elevator.getCurrentFloor());
        }
        elevator.getWaitingList().addAll(elevator.getOnBoard());
        elevator.getOnBoard().clear();

        System.out.println("Emergency stop at floor " + elevator.getCurrentFloor() + ". All passengers have been unloaded.");
    }
}
