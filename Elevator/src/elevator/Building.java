package elevator;

import java.io.Serial;
import java.io.Serializable;

/**
 * The {@code Building} class represents a building with a specified number of floors.
 * @param maxFloor
 * @param minFloor
 */
public record Building(int maxFloor, int minFloor) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
