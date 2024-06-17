package elevator.helper;

import elevator.Elevator;

import java.io.*;

/**
 * The {@code StateHandler} class provides methods to save and load the state of an {@code Elevator} object.
 */
public class StateHandler {

    /**
     * Saves the state of the elevator to a file.
     *
     * @param elevator the elevator object to save
     * @param filename
     * @throws IOException
     */
    public static void saveState(Elevator elevator, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(elevator);
        }
    }

    public static Elevator loadState(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Elevator) in.readObject();
        }
    }
}
