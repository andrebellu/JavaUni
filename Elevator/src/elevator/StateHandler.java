package elevator;

import java.io.*;

public class StateHandler {

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
