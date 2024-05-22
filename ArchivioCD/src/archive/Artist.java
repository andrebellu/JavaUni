package archive;

import it.unibs.fp.mylib.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import static it.unibs.fp.mylib.Strings.capitalize;

/**
 * Class to manage the artists
 * @author Andrea Bellu
 */
public class Artist {
    private String nickname;
    private String birthDate;
    /**
     * Path to the file containing the artists, the path is different if the OS is Windows or not
     */
    private static final String ARTISTS_PATH = System.getProperty("os.name").toLowerCase().contains("win") ? "resources\\artists.txt" : "resources/artists.txt";

    /**
     * Constructor for the Artist class
     * @param nickname the nickname of the artist
     * @param birthDate the birth date of the artist
     */
    public Artist(String nickname, LocalDate birthDate) {
        this.nickname = nickname;
        this.birthDate = birthDate.toString();
    }

    /**
     * Method to add an artist to the file
     * @param artist the artist to add
     * @throws IOException if the file is not found
     */
    public static void addArtist(Artist artist) throws IOException {
        FileWriter fileWriter = new FileWriter(ARTISTS_PATH, true);
        File file = new File(ARTISTS_PATH);

        try {
            if (checkArtist(artist.nickname) != 0) {
                System.out.println("Artist already exists");
            } else {
                fileWriter.write(artist.nickname + ";" + artist.birthDate + "\n");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        fileWriter.close();
    }

    /**
     * Method to remove an artist from the file
     * @param nickname the artist to remove
     * @throws IOException if the file is not found
     */
    public static void removeArtist(String nickname) throws IOException {
        FileWriter fileWriter = new FileWriter(ARTISTS_PATH, true);
        File file = new File(ARTISTS_PATH);
        try {
            if (checkArtist(nickname) == 0) {
                System.out.println("Artist not found");
            } else {
                fileWriter.write(nickname + "\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    /**
     * Method to check if an artist is in the file
     * @param nickname the artist to check
     * @return the line where the artist is located
     * @throws IOException if the file is not found
     */
    public static int checkArtist(String nickname) throws IOException {
        Scanner reader = new Scanner(new File(ARTISTS_PATH));
        int line = 0;
        while (reader.hasNextLine()) {
            line++;
            String data = reader.nextLine();
            String[] values = data.split(";");
            if (values[0].equals(nickname.toLowerCase())) {
                return line;
            }
        }
        return 0;
    }

    /**
     * Method to read the artists from the file
     * @throws FileNotFoundException if the file is not found
     */
    static public void readArtists() throws FileNotFoundException {
        Scanner reader = new Scanner(new File(ARTISTS_PATH));

        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] values = data.split(";");
            System.out.println("Artist: " + capitalize(values[0]) + "\n" +
                    "Birth date: " + values[1] + "\n");
        }
    }

    /**
     * Method to get an artist from the file
     * @param line the line where the artist is located
     * @return the artist
     * @throws FileNotFoundException if the file is not found
     */
    public static Artist getArtist(int line) throws FileNotFoundException {
        Scanner reader = new Scanner(new File(ARTISTS_PATH));
        int i = 0;
        while (reader.hasNextLine()) {
            i++;
            String data = reader.nextLine();
            if (i == line) {
                String[] values = data.split(";");
                return new Artist(values[0], LocalDate.parse(values[1]));
            }
        }
        return null;
    }

    /**
     * Method to update an artist in the file
     * @throws FileNotFoundException if the file is not found
     */
    public static void updateArtist() throws FileNotFoundException {
        Scanner reader = new Scanner(new File(ARTISTS_PATH));
        String nick = Input.readNotEmptyString("Insert the nickname of the artist you want to update: ");
        int line = 0;
        for (int i = 0; reader.hasNextLine(); i++) {
            line++;
            String data = reader.nextLine();
            String[] values = data.split(";");
            if (values[0].equals(nick)) {
                break;
            }
        }
        if (line == 0) {
            System.out.println("Artist not found");
        } else {
            System.out.println("Artist found");
            Artist artist = getArtist(line);
            assert artist != null;
            artist.nickname = Input.readNotEmptyString("Insert the new artist name: ");
            System.out.println("Insert the new birth date: ");
            artist.birthDate = Input.readNotEmptyString("Birth date: ");
        }

    }

    /**
     * Method to delete the file
     */
    public void deleteFile() {
        File file = new File(ARTISTS_PATH);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }

    /**
     * Method to get the nickname of the artist
     * @return the nickname of the artist
     */
    public String getNickname() {
        return nickname;
    }
}
