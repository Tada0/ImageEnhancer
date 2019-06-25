package enhancer.classification;

import enhancer.colorspaces.RGBColorSpace;
import enhancer.filehandlers.MapWriter;

import java.util.HashMap;
import java.util.Map;

public class BulkImageClassificator {

    public void classifyToFile(String filename, double maxDifference, String imagesPath, int indexMin, int indexMax){
        Map<String, RGBColorSpace> classifications = new HashMap<>();
        for(int i = indexMin; i < indexMax; i++){
            RGBColorSpace newClassification = new ImageColorClassifier().getDominantColor(imagesPath + "Image_" + i + ".png", maxDifference);
            classifications.put("Image_" + i + ".png", newClassification);
        }
        MapWriter.write(filename, classifications);
    }

}
