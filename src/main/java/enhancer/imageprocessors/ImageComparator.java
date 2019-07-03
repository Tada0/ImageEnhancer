package enhancer.imageprocessors;

import enhancer.colorspaces.ColorDifferencer;
import enhancer.colorspaces.RGBColorSpace;
import enhancer.filehandlers.MapReader;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class ImageComparator {

    public Map<Integer, String> getChangeMap(String imageDataFile, String referenceDataFile) {
        List<Pair<String, RGBColorSpace>> imageClassificationList = new MapReader().read(imageDataFile);
        List<Pair<String, RGBColorSpace>> referenceClassificationList = new MapReader().read(referenceDataFile);
        return createImageChangeMap(imageClassificationList, referenceClassificationList);
    }

    private Function<Pair<String, RGBColorSpace>, String> toNumber = imageClassification -> imageClassification.getValue0().replaceAll("[^0-9]+", "");

    private Function<Pair<String, RGBColorSpace>, List<Pair<String, Double>>> calculate(List<Pair<String, RGBColorSpace>> referenceClassificationList) {
        return imageClassification -> calculateDifferences(imageClassification, referenceClassificationList);
    }

    private Map<Integer, String> createImageChangeMap(List<Pair<String, RGBColorSpace>> imageClassificationList, List<Pair<String, RGBColorSpace>> referenceClassificationList) {
        return imageClassificationList.parallelStream()
                .collect(toMap(
                        toNumber.andThen(Integer::parseInt),
                        calculate(referenceClassificationList).andThen(this::getMinPair).andThen(Pair::getValue0)
                ));
    }

    private List<Pair<String, Double>> calculateDifferences(Pair<String, RGBColorSpace> imageClassification, List<Pair<String, RGBColorSpace>> referenceClassificationList) {
        List<Pair<String, Double>> differences = new ArrayList<>();
        for (Pair<String, RGBColorSpace> referenceClassification : referenceClassificationList) {
            differences.add(new Pair<>(referenceClassification.getValue0(), new ColorDifferencer().getDifference(imageClassification.getValue1(), referenceClassification.getValue1())));
        }
        return differences;
    }

    private Pair<String, Double> getMinPair(List<Pair<String, Double>> differences) {
        Pair<String, Double> minPair = null;
        for (Pair<String, Double> pair : differences) {
            if (minPair == null || pair.getValue1() < minPair.getValue1())
                minPair = pair;
        }
        return minPair;
    }

}
