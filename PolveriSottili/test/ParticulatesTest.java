import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class ParticulatesTest {
    private Map<Week.WeekDay, Integer> weekValues = new TreeMap<>();

    @BeforeEach
    public void setUp() {
        weekValues.clear();
    }

    @Test
    public void testValuesUnderThresholds(){
        weekValues.put(Week.WeekDay.MONDAY, 10);
    }

}
