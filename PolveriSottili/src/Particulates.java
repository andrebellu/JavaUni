import java.util.Map;
import java.util.TreeMap;

public class Particulates {
    private Map<Week.WeekDay, Integer> weekValues = new TreeMap<>();
    private  int maxThreshold;
    private int meanThreshold;


    public void reset() {
        weekValues.clear();
    }

    public void setMaxThreshold(int maxThreshold) {
        this.maxThreshold = maxThreshold;
    }

    public void setMeanThreshold() {
        int sum = 0;
        for (Week.WeekDay day : weekValues.keySet()) {
            sum += weekValues.get(day);
        }
        meanThreshold = sum / weekValues.size();
    }

    public Particulates() {

    }

    public void addParticulates(Week.WeekDay day, int value) {
        weekValues.put(day, value);
    }

    public Map<Week.WeekDay, Integer> getWeekValues() {
        return weekValues;
    }

    public int getMaxThreshold() {
        return maxThreshold;
    }

    public int getMeanThreshold() {
        return meanThreshold;
    }

    public boolean isMaxThresholdExceeded() {
        for (int value : weekValues.values()) {
            if (value > maxThreshold) {
                return true;
            }
        }
        return false;
    }

    public boolean isMeanThresholdExceeded() {
        for (int value : weekValues.values()) {
            if (value > meanThreshold) {
                return true;
            }
        }
        return false;
    }

    public boolean weekValuesEmpty() {
        return weekValues.isEmpty();
    }


}
