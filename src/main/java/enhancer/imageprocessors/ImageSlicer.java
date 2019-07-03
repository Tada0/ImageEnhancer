package enhancer.imageprocessors;

import enhancer.colorspaces.RGBColorSpace;
import enhancer.filehandlers.DirectoryCreator;
import enhancer.filehandlers.PNGReader;
import enhancer.filehandlers.PNGRepresentation;
import enhancer.filehandlers.PNGWriter;
import java.util.ArrayList;

public class ImageSlicer {

    public boolean sliceIntoChunks(String filepath, String targetDirectory, int chunkSize){
        PNGRepresentation image = new PNGReader().read(filepath);
        ArrayList<ArrayList<RGBColorSpace>> chunks = getChunks(image, chunkSize);
        ArrayList<PNGRepresentation> imagesFromChunks = getImagesFromChunks(chunks, chunkSize);
        if(new DirectoryCreator().create(targetDirectory)){
            saveImages(imagesFromChunks, targetDirectory);
            return true;
        }
        return false;
    }

    private ArrayList<ArrayList<RGBColorSpace>> getRowSlices(PNGRepresentation image, int chunkSize){
        ArrayList<ArrayList<RGBColorSpace>> rowsOfSlices = new ArrayList<>();
        for(int i = 0; i < image.getPixels().size(); i += image.getWidth() * chunkSize){
            rowsOfSlices.add(new ArrayList<>(image.getPixels().subList(i, i + image.getWidth() * chunkSize)));
        }
        return rowsOfSlices;
    }

    private ArrayList<ArrayList<RGBColorSpace>> getChunks(PNGRepresentation image, int chunkSize){
        ArrayList<ArrayList<RGBColorSpace>> chunks = new ArrayList<>();

        ArrayList<ArrayList<RGBColorSpace>> rowsOfSlices = getRowSlices(image, chunkSize);

        for(ArrayList<RGBColorSpace> rowOfSlices : rowsOfSlices){
            for(int column = 0; column < image.getWidth(); column += chunkSize){
                ArrayList<ArrayList<RGBColorSpace>> newChunkRows = getNewChunkRows(image, rowOfSlices, chunkSize, column);
                ArrayList<RGBColorSpace> newChunk = getNewChunk(newChunkRows);
                chunks.add(newChunk);
            }
        }

        return chunks;
    }

    private ArrayList<ArrayList<RGBColorSpace>> getNewChunkRows(PNGRepresentation image, ArrayList<RGBColorSpace> rowOfSlices, int chunkSize, int column){
        ArrayList<ArrayList<RGBColorSpace>> newChunkRows = new ArrayList<>();
        for(int row = 0; row < image.getWidth() * chunkSize; row += image.getWidth()){
            newChunkRows.add(new ArrayList<>(rowOfSlices.subList(column + row, column + row + chunkSize)));
        }
        return newChunkRows;
    }

    private ArrayList<RGBColorSpace> getNewChunk(ArrayList<ArrayList<RGBColorSpace>> newChunkRows){
        ArrayList<RGBColorSpace> newChunk = new ArrayList<>();
        for(ArrayList<RGBColorSpace> chunkRow : newChunkRows){
            newChunk.addAll(chunkRow);
        }
        return newChunk;
    }

    private ArrayList<PNGRepresentation> getImagesFromChunks(ArrayList<ArrayList<RGBColorSpace>> chunks, int chunkSize){
        ArrayList<PNGRepresentation> imagesFromChunks = new ArrayList<>();
        for (ArrayList<RGBColorSpace> chunk : chunks) {
            imagesFromChunks.add(new PNGRepresentation(chunkSize, chunkSize, chunk));
        }
        return imagesFromChunks;
    }

    private void saveImages(ArrayList<PNGRepresentation> images, String targetDirectory){
        for(int i = 0; i < images.size(); i++){
            new PNGWriter().write(images.get(i), targetDirectory + "Image_" + i + ".png");
        }
    }

}
