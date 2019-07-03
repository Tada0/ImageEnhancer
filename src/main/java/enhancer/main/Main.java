package enhancer.main;

import enhancer.classification.BulkImageClassificator;
import enhancer.filehandlers.DirectoryDestructor;
import enhancer.imageprocessors.ImageComparator;
import enhancer.imageprocessors.ImageComposer;
import enhancer.imageprocessors.ImageResizer;
import enhancer.imageprocessors.ImageSlicer;

import java.io.File;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String... args) {
        String workingDirectory = System.getProperty("user.dir");
        String filepath = workingDirectory + "\\assets\\papaj.png";
        String tempDirectory = workingDirectory + "\\assets\\temp\\";
        String dataFilePath = workingDirectory + "\\assets\\ClassificationFiles\\papaj";
        String referenceDataFilePath = workingDirectory + "\\assets\\ClassificationFiles\\referenceClassificationFile";
        String referenceImagesDirectory = workingDirectory + "\\assets\\ReferenceImagesPNG\\";
        String target = workingDirectory + "\\assets\\papaj_target.png";

        new ImageResizer().cut(filepath, 32);
        new ImageSlicer().sliceIntoChunks(filepath, tempDirectory, 32);

        int maxIndex = Objects.requireNonNull(new File(tempDirectory).listFiles()).length;
        new BulkImageClassificator().classifyToFile(dataFilePath, 10.0, tempDirectory, 0, maxIndex);
        new DirectoryDestructor().destroy(tempDirectory);

        Map<Integer, String> imageChangeMap = new ImageComparator().getChangeMap(dataFilePath, referenceDataFilePath);
        new ImageComposer().compose(filepath, referenceImagesDirectory, imageChangeMap, target, 32);
    }
}
