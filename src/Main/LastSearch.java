package Main;

import java.io.*;

public class LastSearch {
    private static final String FILE_NAME = "last_city.txt";

    // Save last searched city to file
    public static void saveLastCity(String city) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(city);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load last searched city from file
    public static String loadLastCity() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
