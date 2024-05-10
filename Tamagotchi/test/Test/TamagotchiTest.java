package Test;

import Tamagotchi.Tamagotchi;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Tamagotchi class
 */
public class TamagotchiTest {

    private Tamagotchi tamagotchi;

    /**
     * Set up a new Tamagotchi object before each test
     */
    @BeforeEach
    public void setUp() {
        tamagotchi = new Tamagotchi("WeTesting");
    }

    /**
     * Test the initial stats of the Tamagotchi
     */
    @Test
    public void testInitialStats() {
        assertEquals(0, tamagotchi.getAge(), 0.01);
        assertEquals(50, tamagotchi.getHunger());
        assertEquals(100, tamagotchi.getSatisfaction());
    }

    /**
     * Test feeding the Tamagotchi
     */
    @Test
    public void testFeedTamagotchi() {
        int hungerBefore = tamagotchi.getHunger();
        int satisfactionBefore = tamagotchi.getSatisfaction();

        tamagotchi.giveCookie(10);

        assertTrue(tamagotchi.getHunger() < hungerBefore);
        assertTrue(tamagotchi.getSatisfaction() < satisfactionBefore);
    }

    /**
     * Test if the Tamagotchi dies when overfed
     */
    @Test
    public void testOverfedTamagotchi() {
        Tamagotchi overfedTamagotchi = new Tamagotchi("Underfed", 0, 10);
        assertTrue(overfedTamagotchi.overfed());
        assertTrue(overfedTamagotchi.dead());
    }

    /**
     * Test if the Tamagotchi dies when underfed
     */
    @Test
    public void testUnderfedTamagotchi() {
        Tamagotchi underfedTamagotchi = new Tamagotchi("Underfed", 100, 10);
        assertTrue(underfedTamagotchi.underfed());
        assertTrue(underfedTamagotchi.dead());
    }

    /**
     * Test if petting the Tamagotchi increases satisfaction and hunger
     */
    @Test
    public void testPetTamagotchi() {
        int satisfactionBefore = tamagotchi.getSatisfaction();
        int hungerBefore = tamagotchi.getHunger();

        tamagotchi.pet(5);

        assertTrue(tamagotchi.getSatisfaction() >= satisfactionBefore);
        assertTrue(tamagotchi.getHunger() > hungerBefore);
    }

    /**
     * Test if the max satisfaction is 100
     */
    @Test
    public void testPetMaxSatisfaction() {
        tamagotchi.pet(100);
        assertEquals(100, tamagotchi.getSatisfaction());
    }

    /**
     * Test if the tamagotchi is happy
     */
    @Test
    public void tamagotchiHappy() {
        final Tamagotchi happyTamagotchi = new Tamagotchi("Happy", 50, 50);
        assertEquals(happyTamagotchi.happinessStatus(), Tamagotchi.Status.HAPPY.toString());
    }

    /**
     * Test if the tamagotchi is sad
     */
    @Test
    public void tamagotchiSad() {
        final Tamagotchi sadTamagotchi = new Tamagotchi("Sad", 20, 20);
        assertEquals(sadTamagotchi.happinessStatus(), Tamagotchi.Status.SAD.toString());
    }

    /**
     * Test if the tamagotchi dies when satisfaction is 0
     */
    @Test
    public void dieZeroSatisfaction() {
        final Tamagotchi deadTamagotchi = new Tamagotchi("Dead", 50, 0);
        assertTrue(deadTamagotchi.dead());
    }

    /**
     * Test if the tamagotchi dies when hunger is 100
     */
    @Test
    public void dieMaxHunger() {
        final Tamagotchi deadTamagotchi = new Tamagotchi("Dead", 100, 50);
        assertTrue(deadTamagotchi.dead());
    }

    /**
     * Test if the user can give a negative number of pets
     */
    @Test
    public void negativePets() {
        assertThrows(IllegalArgumentException.class, () -> tamagotchi.pet(-1));
    }

    /**
     * Test if the user can give a negative number of cookies
     */
    @Test
    public void negativeCookies() {
        assertThrows(IllegalArgumentException.class, () -> tamagotchi.giveCookie(-1));
    }

    /**
     * Test if the simulation stops
     * @throws InterruptedException if the thread is interrupted
     */
    @Test
    public void testStopSimulation() throws InterruptedException {
        Thread simThread = new Thread(tamagotchi::simulateLife);
        simThread.start();

        Thread.sleep(1000);

        tamagotchi.stop();

        Thread.sleep(1000);

        assertFalse(simThread.isAlive());
    }

    /**
     * Test if the age increments
     * @throws InterruptedException if the thread is interrupted
     */
    @Test
    public void ageIncrement() throws InterruptedException {
        tamagotchi.simulateLife();
        Thread.sleep(1000);
        assertEquals(0.1, Math.round(tamagotchi.getAge() * 10.0) / 10.0, 0.01);
    }
}
