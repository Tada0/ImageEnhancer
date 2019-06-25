package enhancer.classification;

import enhancer.colorspaces.ColorDifferencer;
import enhancer.colorspaces.RGBColorSpace;
import enhancer.filehandlers.PNGReader;
import enhancer.filehandlers.PNGRepresentation;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class ImageColorClassifier {

    RGBColorSpace getDominantColor(String filename, double maxDifference){
        PNGRepresentation image = PNGReader.read(filename);
        Map<RGBColorSpace, Integer> colorFrequency = getColorFrequence(image, maxDifference);
        return calculateDominantColor(colorFrequency, image.getPixels().size());
    }

    private Map<RGBColorSpace, Integer> getColorFrequence(PNGRepresentation image, double maxDifference){
        Map<RGBColorSpace, Integer> colorFrequency = new HashMap<>(){{ put(image.getPixels().get(0), 1); }};

        for(int pixelIndex = 1; pixelIndex < image.getPixels().size(); pixelIndex++){
            ArrayList<Pair<Double, RGBColorSpace>> differences = getDifferences(image, colorFrequency, pixelIndex);
            differences = secretoryDifferences(differences, maxDifference);

            if(differences.size() > 0){
                Collections.sort(differences);
                colorFrequency.put(differences.get(0).getValue1(), colorFrequency.get(differences.get(0).getValue1()) + 1);
            } else colorFrequency.put(image.getPixels().get(pixelIndex), 1);
        }

        return colorFrequency;
    }

    private ArrayList<Pair<Double, RGBColorSpace>> getDifferences(PNGRepresentation image, Map<RGBColorSpace, Integer> colorFrequency, int pixelIndex){
        ArrayList<Pair<Double, RGBColorSpace>> differences = new ArrayList<>();
        for(RGBColorSpace key : colorFrequency.keySet()){
            differences.add(new Pair<>(new ColorDifferencer().getDifference(key, image.getPixels().get(pixelIndex)), key));
        }
        return differences;
    }

    private ArrayList<Pair<Double, RGBColorSpace>> secretoryDifferences(ArrayList<Pair<Double, RGBColorSpace>> differences, double maxDifference){
        ArrayList<Pair<Double, RGBColorSpace>> newDifferences = new ArrayList<>();
        for(Pair<Double, RGBColorSpace> difference : differences){
            if(difference.getValue0() < maxDifference) newDifferences.add(difference);
        }
        return newDifferences;
    }

    private RGBColorSpace calculateDominantColor(Map<RGBColorSpace, Integer> colorFrequency, int weight_sum){
        double red = 0;
        double green = 0;
        double blue = 0;

        for(Map.Entry<RGBColorSpace, Integer> entry : colorFrequency.entrySet()){
            red += entry.getKey().getRed() * entry.getValue();
            green += entry.getKey().getGreen() * entry.getValue();
            blue += entry.getKey().getBlue() * entry.getValue();
        }

        return new RGBColorSpace((int)Math.round(red/weight_sum), (int)Math.round(green/weight_sum), (int)Math.round(blue/weight_sum));
    }

}
