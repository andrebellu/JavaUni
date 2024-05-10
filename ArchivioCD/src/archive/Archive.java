package archive;

import java.util.ArrayList;

public class Archive {
    ArrayList<CD> archive = new ArrayList<>();

    public void addCD(CD cd) {
        try
        {
            archive.add(cd);
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }

    public CD getCD(int index) {
        return archive.get(index);
    }

    public ArrayList<CD> getCDs() {
        return archive;
    }

    public int getNumberOfCDs() {
        return archive.size();
    }

    public void removeCD(int index) {
        archive.remove(index);
    }

    public void removeCD(String title) {
        try
        {
            for (CD cd : archive)
            {
                if (cd.title.equals(title))
                {
                    archive.remove(cd);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }


    public void updateCD(int index, CD cd) {
    }

    public Song shuffle() {
        int randomCD = (int) (Math.random() * archive.size());
        CD cd = archive.get(randomCD);
        int randomSong = (int) (Math.random() * cd.tracks.size());
        return cd.tracks.get(randomSong);
    }


}
