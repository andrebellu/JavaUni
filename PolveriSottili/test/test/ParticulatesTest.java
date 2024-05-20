package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import particulates.Particulates;
import particulates.Week;

import static org.junit.jupiter.api.Assertions.*;

public class ParticulatesTest {
    Particulates particulates;

    @BeforeEach
    public void setUp() {
        particulates = new Particulates();
    }

    /**
     * Test if the values are correctly added to the weekValues map and are under the max and mean thresholds
     */
    @Test
    public void testValuesUnderThresholds(){
        int value = 20;
        for (Week.WeekDay day : Week.WeekDay.values()) {
            particulates.addParticulates(day, value);
        }

        particulates.setMaxThreshold(30);
        particulates.setMeanThreshold(25);
        particulates.setValuesMean();

        assertFalse(particulates.isMaxThresholdExceeded());
        assertFalse(particulates.isMeanThresholdExceeded());
    }

    /**
     * Test if the values are correctly added to the weekValues map and are under the mean threshold
     */
    @Test
    public void testValuesUnderMeanThresholds(){
        int value = 20;
        for (Week.WeekDay day : Week.WeekDay.values()) {
            particulates.addParticulates(day, value);
        }

        particulates.setMeanThreshold(25);
        particulates.setValuesMean();

        assertFalse(particulates.isMeanThresholdExceeded());
    }

    /**
     * Test if the values are correctly added to the weekValues map and are over the max threshold
     */
    @Test
    public void testValuesOverMaxThresholds(){
        int value = 40;
        for (Week.WeekDay day : Week.WeekDay.values()) {
            particulates.addParticulates(day, value);
        }

        particulates.setMaxThreshold(30);

        assertTrue(particulates.isMaxThresholdExceeded());
    }

    /**
     * Test if the values are correctly added to the weekValues map and are over the mean threshold
     */
    @Test
    public void testValuesOverMeanThresholds(){
        int value = 40;
        for (Week.WeekDay day : Week.WeekDay.values()) {
            particulates.addParticulates(day, value);
            value++;
        }

        particulates.setMeanThreshold(30);
        particulates.setValuesMean();

        assertTrue(particulates.isMeanThresholdExceeded());
    }

    /**
     * Test if the values are correctly added to the weekValues map and are over the mean threshold but under the max threshold
     */
    @Test
    public void testValuesOverMeanUnderMaxThresholds(){
        int value = 40;
        for (Week.WeekDay day : Week.WeekDay.values()) {
            particulates.addParticulates(day, value);
            value++;
        }

        particulates.setMaxThreshold(50);
        particulates.setMeanThreshold(30);
        particulates.setValuesMean();

        assertFalse(particulates.isMaxThresholdExceeded());
        assertTrue(particulates.isMeanThresholdExceeded());
    }

}
