import it.unibs.fp.mylib.Input;
import it.unibs.fp.mylib.MyMenu;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.time.Year;

import static it.unibs.fp.mylib.BelleStringhe.center;

public class Main {
    static final String[] VOICES = {"Enter data", "Show data", "Show mean threshold", "Show max threshold", "Show if max threshold is exceeded", "Show if mean threshold is exceeded", "Settings"};
    static final String TITLE = "Particulates in the air";
    static final String SETTINGS_TITLE = "Settings - Measure unit: µg/m³";
    static final String[] SETTINGS_VOICES = {"Set max threshold", "Reset thresholds"};
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        int choice;
        MyMenu menu = new MyMenu(center(TITLE, MyMenu.getFrameLength()), VOICES);
        MyMenu settings = new MyMenu(center(SETTINGS_TITLE, MyMenu.getFrameLength()), SETTINGS_VOICES);
        Particulates particulates = new Particulates();
        Week week = null;

        do {
            choice = menu.scegli();
            switch (choice) {
                case 1:
                    if (!particulates.weekValuesEmpty()) {
                        System.out.println("Data already entered, do you want to reset it?");
                        int reset = Input.readInt("Enter 1 to reset, 0 to exit: ");
                        if (reset == 1) {
                            particulates.reset();
                        } else {
                            break;
                        }
                    }
                    int currentYear = Year.now().getValue();
                    int year = Input.readInt("Enter year: ", 0, currentYear);
                    int weekNumber = Input.readInt("Enter week number: ", 1, 53);
                    week = new Week(year, weekNumber);

                    for (Week.WeekDay day : Week.WeekDay.values()) {
                        int value = Input.readInt("Enter particulates for " + day + ": ");
                        particulates.addParticulates(day, value);
                    }

                    particulates.setMeanThreshold();
                    break;
                case 2:
                    if (week == null) {
                        System.out.println("No data entered");
                        break;
                    }
                    particulates.getWeekValues().forEach((k, v) -> System.out.println(k + ": " + v));
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
                    int settingsChoice = settings.scegli();
                    switch (settingsChoice) {
                        case 1:
                            int maxThreshold = Input.readInt("Enter max threshold: ");
                            particulates.setMaxThreshold(maxThreshold);
                            break;
                        case 2:
                            particulates.reset();
                            break;
                    }
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
            }
            if (choice != 0) {
                waitForEnter();
            }
        } while (choice != 0);

    }

    public static void waitForEnter() {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while waiting for input", e);
        }
    }
}
