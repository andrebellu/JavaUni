package it.unibs.fp.mylib;

import java.time.LocalDate;
import java.util.*;

public class Input {
    private static final Scanner scanner = creaScanner();

    private final static String FORMAT_ERROR = "Warning: the entered value is not valid. Please try again.";
    private final static String MIN_ERROR = "Warning: the value must be greater than or equal to ";
    private final static String EMPTY_STRING_ERROR = "Warning: the entered string is empty. Please try again.";
    private final static String BLANK_STRING_ERROR = "Warning: the entered string is blank. Please try again.";
    private final static String MAX_ERROR = "Warning: the value must be less than or equal to ";
    private final static String ALLOWED_MESSAGE = "Warning: the allowed values are ";

    private final static char YES = 'Y';
    private final static char NO = 'N';


    private static Scanner creaScanner() {
        return new Scanner(System.in);
    }

    public static String readNotEmptyString(String message) {
        System.out.print(message);
        String line = scanner.nextLine();
        if (line.isEmpty() || line.isBlank()) {
            String messageError = line.isEmpty() ? EMPTY_STRING_ERROR : BLANK_STRING_ERROR;
            System.out.println(messageError);
            return readNotEmptyString(message);
        }
        return line;
    }

//    public static String readNotEmptyString(String message) {
//        boolean stop = false;
//        String string = null;
//        do {
//            string = readNotEmptyString(message);
//            string = string.trim();
//            if (!string.isEmpty())
//                stop = true;
//            else
//                System.out.println(EMPTY_STRING_ERROR);
//        } while (!stop);
//
//        return string;
//    }

    public static char readChar(String message) {
        boolean stop = false;
        char value = '\0';
        do {
            System.out.print(message);
            String line = scanner.next();
            if (!line.isEmpty()) {
                value = line.charAt(0);
                stop = true;
            } else
                System.out.println(FORMAT_ERROR);
        } while (!stop);
        return value;
    }

    public static char readUpperChar(String message, String allowed) {
        boolean stop = false;
        char value = '\0';
        do {
            value = readChar(message);
            value = Character.toUpperCase(value);
            if (allowed.indexOf(value) != -1)
                stop = true;
            else
                System.out.println(ALLOWED_MESSAGE + allowed);
        } while (!stop);
        return value;
    }


    public static int readInt(String message) {
        boolean stop = false;
        int value = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print(message);
            try {
                value = scanner.nextInt();
                System.out.println(value);
                stop = true;
            } catch (InputMismatchException e) {
                System.out.println(FORMAT_ERROR);
                scanner.nextLine();
            }
        } while (!stop);
        return value;
    }

    public static int readPositiveInt(String message) {
        return readIntMin(message, 1);
    }

    public static int readNonNegativeInt(String message) {
        return readIntMin(message, 0);
    }


    public static int readIntMin(String message, int minimo) {
        boolean stop = false;
        int value = 0;
        do {
            value = readInt(message);
            if (value >= minimo)
                stop = true;
            else
                System.out.println(MIN_ERROR + minimo);
        } while (!stop);

        return value;
    }

    public static int readInt(String message, int minimo, int massimo) {
        boolean stop = false;
        int value = 0;
        do {
            value = readInt(message);
            if (value >= minimo && value <= massimo)
                stop = true;
            else if (value < minimo)
                System.out.println(MIN_ERROR + minimo);
            else
                System.out.println(MAX_ERROR + massimo);
        } while (!stop);

        return value;
    }


    public static double readDouble(String message) {
        boolean stop = false;
        double value = 0;
        do {
            System.out.print(message);
            try {
                value = scanner.nextDouble();
                stop = true;
            } catch (InputMismatchException e) {
                System.out.println(FORMAT_ERROR);
                String daButtare = scanner.next();
            }
        } while (!stop);
        return value;
    }

    public static double readDoubleMin(String message, double minimo) {
        boolean stop = false;
        double value = 0;
        do {
            value = readDouble(message);
            if (value >= minimo)
                stop = true;
            else
                System.out.println(MIN_ERROR + minimo);
        } while (!stop);

        return value;
    }


    public static boolean yesOrNo(String message) {
        String messageYesNo = message + " (" + YES + "/" + NO + ")";
        char value = readUpperChar(messageYesNo, String.valueOf(YES) + String.valueOf(NO));

        return value == YES;
    }
}
