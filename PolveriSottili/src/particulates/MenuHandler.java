package particulates;

import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;

import java.time.Year;

import static it.unibs.fp.mylib.Strings.center;

/**
 * This class represents the menu handler for managing particulate data.
 * @author Andrea Bellu
 */
public class MenuHandler {
    private Particulates particulates;
    private Week week;
    private MyMenu settingsMenu;
    private static final String SETTINGS_TITLE = "Settings - Measure unit: µg/m³";
    private static final String[] SETTINGS_VOICES = {"Set max threshold", "Reset thresholds"};

    /**
     * Constructs a new instance of MenuHandler with the given particulates instance.
     * @param particulates
     */
    public MenuHandler(Particulates particulates) {
        this.particulates = particulates;
        this.settingsMenu = new MyMenu(center(SETTINGS_TITLE, MyMenu.getFrameLength()), SETTINGS_VOICES);
    }

    /**
     * Handles the menu choice using a switch statement.
     * @param choice The choice made by the user.
     */
    public void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                handleEnterData();
                break;
            case 2:
                if (week == null || particulates.getWeekValues().isEmpty()) {
                    System.out.println("No data entered");
                    break;
                }
                handleShowData();
                break;
            case 3:
                if (particulates.getWeekValues().isEmpty()) {
                    System.out.println("No data entered: mean threshold not available");
                    break;
                }
                System.out.println("Mean threshold: " + particulates.getMeanThreshold());
                break;
            case 4:
                if (particulates.getMaxThreshold() == 0) {
                    System.out.println("Max threshold not set");
                    break;
                }
                System.out.println("Max threshold: " + particulates.getMaxThreshold());
                break;
            case 5:
                if (particulates.getMaxThreshold() == 0){
                    System.out.println("Max threshold not set");
                    break;
                }

                String maxOut = particulates.isMaxThresholdExceeded() ? "Yes" : "No";
                System.out.println("Max threshold exceeded: " + maxOut);
                break;
            case 6:
                if (particulates.getMeanThreshold() == 0){
                    System.out.println("Max threshold not set");
                    break;
                }

                String meanOut = particulates.isMeanThresholdExceeded() ? "Yes" : "No";
                System.out.println("Mean threshold exceeded: " + meanOut);
                break;
            case 7:
                handleSettings();
                break;
            case 0:
                System.out.println("Goodbye!");
                break;
        }
    }

    /**
     * Handles the entering of data for the week.
     */
    private void handleEnterData() {
        int year = Input.readInt("Enter year: ", 1900, Year.now().getValue());
        int weekNumber = Input.readInt("Enter week number: ");
        week = new Week(year, weekNumber);
        for (Week.WeekDay day : Week.WeekDay.values()) {
            int value = Input.readInt("Enter value for " + day + ": ");
            particulates.addParticulates(day, value);
        }
    }

    /**
     * Handles the showing of data for the week.
     */
    private void handleShowData() {
        if (particulates.weekValuesEmpty()) {
            System.out.println("No data entered");
        } else {
            particulates.printWeekValues();
        }
    }

    /**
     * Handles the settings menu.
     */
    private void handleSettings() {
        int settingsChoice = settingsMenu.scegli();
        switch (settingsChoice) {
            case 1:
                int maxThreshold = Input.readInt("Enter max threshold: ");
                particulates.setMaxThreshold(maxThreshold);
                break;
            case 2:
                particulates.reset();
                break;
        }
    }
}

