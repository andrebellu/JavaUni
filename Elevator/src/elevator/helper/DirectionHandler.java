package elevator.helper;

import elevator.Elevator;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * Handles the logic for determining if an elevator should change direction based on the efficiency
 * ratings of floors.
 */
public class DirectionHandler {
    private static final float WAITING_WEIGHT = 10.0F;
    private static final float ONBOARD_WEIGHT = 5.0F;
    private static final float DISTANCE_PENALTY = 1.0F;
    private static final float BASE_RATING = 50.0F;

    /**
     * Determines if the elevator should change direction based on the current efficiency ratings of floors.
     *
     * @param elevator the elevator object
     * @return true if the elevator should change direction, false otherwise
     */
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

    /**
     * Rates each floor based on the number of waiting and onboard passengers and the distance from the current floor.
     *
     * @param elevator the elevator object
     * @return a map of floors and their respective efficiency ratings
     */
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

            if (waitingCount > 0 || onboardCount > 0) {
                rating = BASE_RATING;
                rating += waitingCount * WAITING_WEIGHT;
                rating += onboardCount * ONBOARD_WEIGHT;
                rating -= distance * DISTANCE_PENALTY;
            }

            floorMap.put(floor, rating);
        });

        return floorMap;
    }
}
