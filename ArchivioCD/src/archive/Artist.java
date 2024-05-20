package archive;

import it.unibs.fp.mylib.Input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import static it.unibs.fp.mylib.Strings.capitalize;

public class Artist {
    private String nickname;
    private String birthDate;
    private static final String ARTISTS_PATH = System.getProperty("os.name").toLowerCase().contains("win") ? "resources\\artists.txt" : "resources/artists.txt";

    public Artist(String nickname, LocalDate birthDate) {
        this.nickname = nickname;
        this.birthDate = birthDate.toString();
    }

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

    static public void readArtists() throws FileNotFoundException {
        Scanner reader = new Scanner(new File(ARTISTS_PATH));

        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            String[] values = data.split(";");

            if ((values[0] + " " + values[1]).equals(values[2]) || values[1].equals(values[2])) {
                System.out.printf("%s - %s%n", capitalize(values[2]), values[3]);
            } else {
                System.out.printf("%s - %s%n", capitalize(values[0]), values[1]);
            }

        }
    }

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

    public static void updateArtist() throws FileNotFoundException {
        Scanner reader = new Scanner(new File(ARTISTS_PATH));
        String nick = Input.readNotEmptyString("Insert the nickname of the artist you want to update: ");
        int line = 0;
        for (int i = 0; reader.hasNextLine(); i++) {
            line++;
            String data = reader.nextLine();
            String[] values = data.split(";");
            if (values[2].equals(nick)) {
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

    public void deleteFile() {
        File file = new File(ARTISTS_PATH);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }

    public String getNickname() {
        return nickname;
    }
}
