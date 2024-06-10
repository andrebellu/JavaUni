package elevator;

import java.io.Serial;
import java.io.Serializable;

public record Building(int maxFloor, int minFloor) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
