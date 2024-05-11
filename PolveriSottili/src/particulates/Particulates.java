package particulates;

import java.util.Map;
import java.util.TreeMap;

/**
 * This class represents the particulates data and related operations.
 */
public class Particulates {
    /**
     * Map containing the particulate values for each day of the week.
     */
    private Map<Week.WeekDay, Integer> weekValues = new TreeMap<>();
    /**
     * The maximum threshold for particulate values.
     */
    private int maxThreshold;
    /**
     * The mean threshold for particulate values.
     */
    private int meanThreshold;

    /**
     * Resets the particulate data.
     */
    public void reset() {
        weekValues.clear();
    }

    /**
     * Sets the maximum threshold for particulate values.
     *
     * @param maxThreshold The maximum threshold value to set.
     */
    public void setMaxThreshold(int maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    /**
     * Calculates and sets the mean threshold for particulate values.
     */
    public void setMeanThreshold() {
        int sum = 0;
        for (Week.WeekDay day : weekValues.keySet()) {
            sum += weekValues.get(day);
        }
        meanThreshold = sum / weekValues.size();
    }

    /**
     * Constructs a new instance of particulates.Particulates.
     */
    public Particulates() {

    }

    /**
     * Adds particulate value for a specific day of the week.
     *
     * @param day   The day of the week.
     * @param value The particulate value for the day.
     */
    public void addParticulates(Week.WeekDay day, int value) {
        weekValues.put(day, value);
    }

    /**
     * Gets the map containing the particulate values for each day of the week.
     *
     * @return The map containing the particulate values.
     */
    public Map<Week.WeekDay, Integer> getWeekValues() {
        return weekValues;
    }

    /**
     * Gets the maximum threshold for particulate values.
     *
     * @return The maximum threshold value.
     */
    public int getMaxThreshold() {
        return maxThreshold;
    }

    /**
     * Gets the mean threshold for particulate values.
     *
     * @return The mean threshold value.
     */
    public int getMeanThreshold() {
        return meanThreshold;
    }

    /**
     * Checks if any particulate value exceeds the maximum threshold.
     *
     * @return True if any particulate value exceeds the maximum threshold, false otherwise.
     */
    public boolean isMaxThresholdExceeded() {
        for (int value : weekValues.values()) {
            if (value > maxThreshold) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any particulate value exceeds the mean threshold.
     *
     * @return True if any particulate value exceeds the mean threshold, false otherwise.
     */
    public boolean isMeanThresholdExceeded() {
        for (int value : weekValues.values()) {
            if (value > meanThreshold) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the map containing the particulate values for each day of the week is empty.
     *
     * @return True if the map is empty, false otherwise.
     */
    public boolean weekValuesEmpty() {
        return weekValues.isEmpty();
    }
}
