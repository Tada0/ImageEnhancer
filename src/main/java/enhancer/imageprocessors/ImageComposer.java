package enhancer.imageprocessors;

import enhancer.colorspaces.RGBColorSpace;
import enhancer.filehandlers.PNGReader;
import enhancer.filehandlers.PNGRepresentation;
import enhancer.filehandlers.PNGWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ImageComposer {

    public void compose(String originalImagePath, String referenceImagesDirectory, Map<Integer, String> imageChangeMap, String composedImagePath, int chunkSize) {
        PNGRepresentation originalImage = PNGReader.read(originalImagePath);
        List<RGBColorSpace> newPixels = getNewPixels(originalImage, referenceImagesDirectory, imageChangeMap, chunkSize);
        PNGRepresentation newImage = new PNGRepresentation(originalImage.getWidth(), originalImage.getHeight(), newPixels);
        PNGWriter.write(newImage, composedImagePath);
    }

    public class Rows {
        private int index;
        private List<Row> rows;

        Rows(int index, List<Row> rows) {
            this.index = index;
            this.rows = rows;
        }

        public int getIndex() {
            return index;
        }

        public List<Row> getRows() {
            return rows;
        }
    }

    List<RGBColorSpace> getNewPixels(PNGRepresentation originalImage, String referenceImagesDirectory, Map<Integer, String> imageChangeMap, int chunkSize) {
        List<RGBColorSpace> newPixels = new ArrayList<>();


        IntStream.range(0, originalImage.getHeight() / chunkSize)
                .parallel()
                .mapToObj(i -> {
                    List<Chunk> chunkRowPixels = new ArrayList<>();

                    for (int j = 0; j < originalImage.getWidth() / chunkSize; j++) {
                        PNGRepresentation referenceImage = loadImage(originalImage, referenceImagesDirectory, imageChangeMap, chunkSize, i, j);
                        chunkRowPixels.add(new Chunk(referenceImage, chunkSize));
                    }

                    List<Row> rowsSource = new ArrayList<>();
                    for (int k = 0; k < chunkSize; k++) {
                        rowsSource.add(new Row(chunkRowPixels, k));
                    }
                    return new Rows(i, rowsSource);
                })
                .sorted(Comparator.comparingInt(Rows::getIndex))
                .forEachOrdered(fromRows -> {
                    for (Row row : fromRows.getRows()) {
                        newPixels.addAll(row.getPixels());
                    }
                });

/*
        for (int i = 0; i < originalImage.getHeight() / chunkSize; i++) {
            List<Chunk> chunkRowPixels = new ArrayList<>();
            for (int j = 0; j < originalImage.getWidth() / chunkSize; j++) {
                PNGRepresentation referenceImage = loadImage(originalImage, referenceImagesDirectory, imageChangeMap, chunkSize, i, j);
                chunkRowPixels.add(new Chunk(referenceImage, chunkSize));
            }
            List<Row> rowsSource = new ArrayList<>();
            for (int k = 0; k < chunkSize; k++) {
                rowsSource.add(new Row(chunkRowPixels, k));
            }
            for (Row row : rowsSource) {
                newPixels.addAll(row.getPixels());
            }
        }
*/
        return newPixels;
    }

    private PNGRepresentation loadImage(PNGRepresentation originalImage, String referenceImagesDirectory, Map<Integer, String> imageChangeMap, int chunkSize, int i, int j) {
        return PNGReader.read(referenceImagesDirectory + imageChangeMap.get(i * (originalImage.getWidth() / chunkSize) + j));
    }

}