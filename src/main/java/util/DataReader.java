package main.java.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    public static List<String> readDataFromFile(String filePath){
        Path path = Paths.get(filePath);
        ArrayList<String> data = new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(path);
            String value = "";
            while((value = reader.readLine()) != null){
                data.add(value);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return data;
    }

}
