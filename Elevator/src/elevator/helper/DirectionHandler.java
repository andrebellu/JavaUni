package elevator.helper;

import elevator.Elevator;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class DirectionHandler {
    private static final float WAITING_WEIGHT = 1.0F;
    private static final float ONBOARD_WEIGHT = 1.5F;
    private static final float DISTANCE_PENALTY = 0.5F;

    public static boolean shouldChangeDirection(Elevator elevator) {
        Map<Integer, Float> floorEfficiencyRating = rateFloors(elevator);

        float maxRating = Float.NEGATIVE_INFINITY;
        int targetFloor = elevator.getCurrentFloor();

        for (Map.Entry<Integer, Float> entry : floorEfficiencyRating.entrySet()) {
            if (entry.getValue() > maxRating) {
                maxRating = entry.getValue();
                targetFloor = entry.getKey();
            }
        }

        return elevator.getCurrentFloor() > targetFloor && elevator.getDirection().equals("up") ||
                elevator.getCurrentFloor() < targetFloor && elevator.getDirection().equals("down");
    }

    private static Map<Integer, Float> rateFloors(Elevator elevator) {
        Map<Integer, Float> floorMap = new TreeMap<>();

        IntStream.rangeClosed(elevator.getMinFloor(), elevator.getMaxFloor()).forEach(floor -> {
            float rating = 0F;
            int distance = Math.abs(elevator.getCurrentFloor() - floor);

            long waitingCount = elevator.getWaitingList().stream()
                    .filter(person -> person.getCurrentFloor() == floor)
                    .count();

            long onboardCount = elevator.getOnBoard().stream()
                    .filter(person -> person.getDestinationFloor() == floor)
                    .count();

            rating += waitingCount * WAITING_WEIGHT;
            rating += onboardCount * ONBOARD_WEIGHT;
            rating -= distance * DISTANCE_PENALTY;

            floorMap.put(floor, rating);
        });

        return floorMap;
    }
}
