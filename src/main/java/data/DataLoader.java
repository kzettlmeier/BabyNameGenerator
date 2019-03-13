package data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static List<String> loadDataFromFileIntoList(String fileName, int orderSize) {
        try {
            String startEndChar = "_";
            String startEndChars = startEndChar.repeat(orderSize);
            List<String> names = new ArrayList<>();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream is = classLoader.getResourceAsStream(fileName);
            InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String line; (line = reader.readLine()) != null;) {
                names.add(startEndChars + line + startEndChars);
            }
            return names;
        } catch(Exception ex) {
            System.out.println("Error reading file: " + ex.getMessage());
        }

        return new ArrayList<>();
    }
}
