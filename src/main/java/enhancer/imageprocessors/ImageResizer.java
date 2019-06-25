package enhancer.imageprocessors;

import enhancer.colorspaces.RGBColorSpace;
import enhancer.filehandlers.PNGReader;
import enhancer.filehandlers.PNGRepresentation;
import enhancer.filehandlers.PNGWriter;

import java.util.ArrayList;
import java.util.List;

public class ImageResizer {

    public void cut(String filePath, int chunkSize){
        PNGRepresentation image = PNGReader.read(filePath);
        PNGRepresentation cut_image = processImage(image, chunkSize);
        if(!(cut_image == image)) PNGWriter.write(cut_image, filePath);
    }

    private PNGRepresentation processImage(PNGRepresentation image, int chunk){
        int newWidth = getNewDimension(image.getWidth(), chunk);
        int newHeight = getNewDimension(image.getHeight(), chunk);
        List<RGBColorSpace> newPixels = getNewPixels(image, newWidth, newHeight);
        return image.getPixels() == newPixels ? image : new PNGRepresentation(newWidth, newHeight, newPixels);
    }

    private int getNewDimension(int oldDimension, int chunk){
        return oldDimension - (oldDimension % chunk);
    }

    private List<RGBColorSpace> getNewPixels(PNGRepresentation image, int newWidth, int newHeight){
        List<RGBColorSpace> cutHorizontally = newWidth == image.getWidth() ? image.getPixels() : cutHorizontally(image, newWidth);
        return newHeight == image.getHeight() ? cutHorizontally : cutVertically(cutHorizontally, newWidth, newHeight);
    }

    private List<RGBColorSpace> cutHorizontally(PNGRepresentation image, int newWidth){

        List<RGBColorSpace> newPixels = new ArrayList<>();

        for(int i = 0; i < image.getWidth() * image.getHeight(); i += image.getWidth()){
            for(int j = 0; j < newWidth; j++){
                newPixels.add(image.getPixels().get(i + j));
            }
        }

        return newPixels;
    }

    private List<RGBColorSpace> cutVertically(List<RGBColorSpace> oldPixels, int newWidth, int newHeight){
        List<RGBColorSpace> newPixels = new ArrayList<RGBColorSpace>();

        for(int i = 0; i < newWidth * newHeight; i++){
            newPixels.add(oldPixels.get(i));
        }

        return newPixels;
    }

}
