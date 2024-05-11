package particulates;

/**
 * This class represents a week in a specific year and provides methods to access its properties.
 */
public class Week {

    /**
     * Enum representing the days of the week.
     */
    public enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    /**
     * The year of the week.
     */
    private int year;

    /**
     * The week number within the year.
     */
    private int weekNumber;

    /**
     * Constructs a new instance of particulates.Week with the given year and week number.
     *
     * @param year       The year of the week.
     * @param weekNumber The week number within the year.
     */
    public Week(int year, int weekNumber) {
        this.year = year;
        this.weekNumber = weekNumber;
    }

    /**
     * Gets the year of the week.
     *
     * @return The year of the week.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the week.
     *
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the week number within the year.
     *
     * @return The week number.
     */
    public int getWeekNumber() {
        return weekNumber;
    }

    /**
     * Sets the week number within the year.
     *
     * @param weekNumber The week number to set.
     */
    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }
}
