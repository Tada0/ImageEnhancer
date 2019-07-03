package enhancer.filehandlers;

import enhancer.colorspaces.RGBColorSpace;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class MapWriter {

    public void write(String filename, Map<String, RGBColorSpace> map) {
        try (FileWriter fileWriter = new FileWriter(filename)) {
            for (Map.Entry<String, RGBColorSpace> entry: map.entrySet()) fileWriter.write(entry.getKey() + "-" + entry.getValue().toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
