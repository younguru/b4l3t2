import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            objectOutputStream.writeObject(gameProgress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String path, List<String> pathsList) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(path))) {
            for (String s : pathsList) {
                File file = new File(s);
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
            for (String s : pathsList) {
                File file = new File(s);
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fs = File.separator;
        GameProgress[] gameProgress = new GameProgress[3];
        String path;
        List<String> pathsList = new ArrayList<>();
        gameProgress[0] = new GameProgress(90, 7, 20, 15);
        gameProgress[1] = new GameProgress(79, 3, 7, 20);
        gameProgress[2] = new GameProgress(100, 1, 2, 56);
        for (int i = 0; i < gameProgress.length; i++) {
            path = fs + "Games" + fs + "savegames" + fs + "save" + i + ".dat";
            saveGame(path, gameProgress[i]);
            pathsList.add(path);
        }
        zipFiles(fs + "Games" + fs + "zip.zip", pathsList);
    }
}