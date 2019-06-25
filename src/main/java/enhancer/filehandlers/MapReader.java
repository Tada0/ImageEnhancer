package enhancer.filehandlers;

import enhancer.colorspaces.RGBColorSpace;
import org.javatuples.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MapReader {

    public static ArrayList<Pair<String, RGBColorSpace>> read(String filepath){
        ArrayList<Pair<String, RGBColorSpace>> lines = new ArrayList<>();
        try {
            List<String> allLines = Files.readAllLines(Paths.get(filepath));
            for (String line : allLines) {
                lines.add(lineProcess(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static Pair<String, RGBColorSpace> lineProcess(String line){
        String[] parts = line.split("-");
        return new Pair<>(parts[0], new RGBColorSpace(parts[1]));
    }

}
