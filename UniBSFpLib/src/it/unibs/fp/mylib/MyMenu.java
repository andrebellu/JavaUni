package it.unibs.fp.mylib;

/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in fondo al menu
*/

public class MyMenu {
    final private static String FRAME = "████████████████████████████████████████████████████████████";
    final private static String EXIT = "0\tExit";
    final private static String CHOICE_MESSAGE = "Enter your choice: ";

    private final String title;
    private final String[] voices;

    public int getVoicesLength() {
        return voices.length;
    }

    public static int getFrameLength() {
        return FRAME.length();
    }

    public MyMenu(String titolo, String[] voci) {
        this.title = titolo;
        this.voices = voci;
    }

    public int scegli() {
        stampaMenu();
        return Input.readInt(CHOICE_MESSAGE, 0, voices.length);
    }

    public void stampaMenu() {
        System.out.println(FRAME);
        System.out.println(title);
        System.out.println(FRAME);
        for (int i = 0; i < voices.length; i++) {
            System.out.println((i + 1) + "\t" + voices[i]);
        }
        System.out.println();
        System.out.println(EXIT);
        System.out.println();
    }

}

