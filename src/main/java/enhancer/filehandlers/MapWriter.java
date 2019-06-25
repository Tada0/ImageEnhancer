package enhancer.filehandlers;

import enhancer.colorspaces.RGBColorSpace;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class MapWriter {

    public static void write(String filename, Map<String, RGBColorSpace> map) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (String key : map.keySet()) fileWriter.write(key + "-" + map.get(key).toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
