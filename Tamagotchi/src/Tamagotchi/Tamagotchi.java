package Tamagotchi;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents a virtual pet in the Tamagotchi Simulator program.
 * A Tamagotchi has attributes like age, hunger, satisfaction, and a name.
 * It requires care through feeding and petting to stay happy and alive.
 */
public class Tamagotchi {
    /**
     * The Tamagotchi's name
     */
    public String name;

    /**
     * The Tamagotchi's current age
     */
    private float age;

    /**
     * The Tamagotchi's current hunger level (0-100)
     */
    private int hunger;

    /**
     * The Tamagotchi's current satisfaction level (0-100)
     */
    private int satisfaction;

    /**
     * Flag indicating if the Tamagotchi is dead
     */
    private boolean isDead = false;

    /**
     * Threshold for the Tamagotchi to be considered happy
     */
    public final int SATISFACTION_THRESHOLD = 30;

    /**
     * Maximum hunger level before considered overfed
     */
    public final int HUNGER_THRESHOLD_MAX = 90;

    /**
     * Minimum hunger level before considered hungry
     */
    public final int HUNGER_THRESHOLD_MIN = 30;

    /**
     * Name getter method
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter method
     * @param name The name for the Tamagotchi
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Enum representing the Tamagotchi's happiness status
     */
    public enum Status {
        /**
         * The Tamagotchi is happy
         */
        HAPPY,
        /**
         * The Tamagotchi is sad
         */
        SAD,
        /**
         * The Tamagotchi is dead
         */
        DEAD
    }

    /**
     * Creates a new Tamagotchi with the given name.
     *
     * @param name The name for the Tamagotchi
     */
    public Tamagotchi(String name) {
        this.name = name;
        this.age = 0;
        this.hunger = 50;
        this.satisfaction = 100;
    }

    /**
     * Creates a new Tamagotchi with the given name, hunger, and satisfaction levels.
     *
     * @param name         The name for the Tamagotchi
     * @param hunger       The hunger level for the Tamagotchi
     * @param satisfaction The satisfaction level for the Tamagotchi
     */
    public Tamagotchi(String name, int hunger, int satisfaction) {
        this.name = name;
        this.age = 0;
        this.hunger = hunger;
        this.satisfaction = satisfaction;
    }

    /**
     * Age getter method
     *
     * @return age
     */
    public float getAge() {
        return age;
    }

    /**
     * hunger getter method
     *
     * @return hunger
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Satisfaction getter method
     *
     * @return satisfaction
     */
    public int getSatisfaction() {
        return satisfaction;
    }

    /**
     * Feeds the Tamagotchi the specified number of cookies.
     * Reduces hunger but also decreases satisfaction slightly.
     *
     * @param cookies The number of cookies to feed
     * @return An array containing the number of cookies eaten, current hunger, and current satisfaction
     */
    public int[] giveCookie(int cookies) {
        if (cookies < 0) {
            throw new IllegalArgumentException("Cookies cannot be negative");
        }

        if (this.hunger > 70) {
            this.hunger -= (int) (cookies * this.hunger * 0.05);
        } else {
            this.hunger -= (int) (cookies * this.hunger * 0.1);
        }
        this.satisfaction -= (int) (cookies * 0.25);
        return new int[]{cookies, this.hunger, this.satisfaction};
    }

    /**
     * Pets the Tamagotchi the specified number of times.
     * Increases satisfaction and reduces hunger slightly.
     *
     * @param pets The number of times to pet
     * @return An array containing the number of pets given and current satisfaction
     */
    public int[] pet(int pets) {
        if (pets < 0) {
            throw new IllegalArgumentException("Pets cannot be negative");
        }

        this.satisfaction = Math.min(this.satisfaction + pets, 100);
        this.hunger += pets / 2;
        return new int[]{pets, this.satisfaction};
    }

    /**
     * Simulates the passage of time for the Tamagotchi.
     * Increases age, hunger, and decreases satisfaction.
     */
    public void passTime() {
        if (!isDead) {
            this.age += 0.1F;
            this.hunger += 1;
            this.satisfaction -= 1;
        }
    }

    /**
     * Checks if the Tamagotchi is dead based on hunger or satisfaction levels.
     *
     * @return True if the Tamagotchi is dead, false otherwise
     */
    public boolean dead() {
        return overfed() || underfed() || depressed();
    }

    /**
     * Checks if the Tamagotchi is overfed.
     *
     * @return True if the Tamagotchi is overfed, false otherwise
     */
    public boolean overfed() {
        return this.hunger <= 0;
    }

    /**
     * Checks if the Tamagotchi is underfed.
     *
     * @return True if the Tamagotchi is underfed, false otherwise
     */
    public boolean underfed() {
        return this.hunger >= 100;
    }

    /**
     * Checks if the Tamagotchi's satisfaction level is too low.
     *
     * @return True if the Tamagotchi is depressed, false otherwise
     */
    private boolean depressed() {
        return this.satisfaction <= 0;
    }

    /**
     * Determines the Tamagotchi's current happiness status based on satisfaction, hunger, and thresholds.
     *
     * @return String representation of the happiness status (HAPPY or SAD)
     */
    public String happinessStatus() {
        if (!isDead) {
            return this.satisfaction > SATISFACTION_THRESHOLD && this.hunger < HUNGER_THRESHOLD_MAX && this.hunger > HUNGER_THRESHOLD_MIN ? Status.HAPPY.toString() : Status.SAD.toString();
        } else {
            return Status.DEAD.toString();
        }
    }

    /**
     * Displays the Tamagotchi's current status including name, age, hunger, satisfaction, and happiness status.
     */
    public void viewStatus() {
        try {
            while (System.in.available() > 0 && System.in.read() != '\n') {}
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Name: " + this.name + "\nAge: " + Math.round(this.age) + "\nHunger: " + this.hunger + "\nSatisfaction: " + this.satisfaction + "\nStatus: " + happinessStatus());
        System.out.println("Press Enter to return to the menu...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simulates the Tamagotchi's life in a separate thread.
     * This method makes the Tamagotchi age, get hungry, and less satisfied over time.
     */
    ExecutorService service = Executors.newFixedThreadPool(4);

    /**
     * Simulates the Tamagotchi's life in a separate thread.
     */
    public void simulateLife() {
        service.submit(() -> {
            while (this.age < 30) {
                passTime();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Monitors the Tamagotchi's life in a separate thread.
     * This method checks if the Tamagotchi is dead and prints a message if it is.
     */
    public void isDead() {
        service.submit(() -> {
            while (!this.dead()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isDead = true;
            service.shutdown();
            Thread.currentThread().interrupt();
        });
    }

    /**
     * Shuts down the thread pool used for simulation and monitoring.
     */
    public void stop() {
        service.shutdown();
    }
}
