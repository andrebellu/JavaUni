package elevator;

import java.io.Serializable;

public class Building implements Serializable {
    private int maxFloor;
    private int minFloor;

    public Building(int maxFloor, int minFloor) {
        this.maxFloor = maxFloor;
        this.minFloor = minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getMinFloor() {
        return minFloor;
    }
}
