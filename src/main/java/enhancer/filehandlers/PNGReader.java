package enhancer.filehandlers;

import enhancer.colorspaces.RGBColorSpace;
import ij.IJ;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PNGReader {

    public PNGRepresentation read(String filePath){
        BufferedImage image = IJ.openImage(filePath).getBufferedImage();
        return getData(image);
    }

    private PNGRepresentation getData(BufferedImage image){
        int imageWidth = getWidth(image);
        int imageHeight = getHeight(image);
        List<RGBColorSpace> pixels = getPixels(image, imageWidth, imageHeight);
        return new PNGRepresentation(imageWidth, imageHeight, pixels);
    }

    private int getWidth(BufferedImage image){
        return image.getWidth();
    }

    private int getHeight(BufferedImage image){
        return image.getHeight();
    }

    private List<RGBColorSpace> getPixels(BufferedImage image, int imageWidth, int imageHeight){
        List<RGBColorSpace> pixels = new ArrayList<>();

        for(int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                pixels.add(new RGBColorSpace(pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue()));
            }
        }

        return pixels;
    }

}
