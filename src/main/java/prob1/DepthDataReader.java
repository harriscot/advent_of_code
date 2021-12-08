package main.java.prob1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class DepthDataReader {

    List<Integer> getDepthData() {
        Path path = Paths.get("src/main/java/prob1/day_1_data.txt");
        LinkedList<Integer> result = new LinkedList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(path);
            String value = "";
            while((value = reader.readLine()) != null){
                Integer datum = Integer.parseInt(value);
                result.add(datum);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return result;
    }

}
