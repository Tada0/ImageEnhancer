package enhancer.filehandlers;

import enhancer.colorspaces.RGBColorSpace;
import ij.IJ;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PNGReader {

    public static PNGRepresentation read(String filePath){
        BufferedImage image = IJ.openImage(filePath).getBufferedImage();
        return getData(image);
    }

    private static PNGRepresentation getData(BufferedImage image){
        int image_width = getWidth(image);
        int image_height = getHeight(image);
        ArrayList<RGBColorSpace> pixels = getPixels(image, image_width, image_height);
        return new PNGRepresentation(image_width, image_height, pixels);
    }

    private static int getWidth(BufferedImage image){
        return image.getWidth();
    }

    private static int getHeight(BufferedImage image){
        return image.getHeight();
    }

    private static ArrayList<RGBColorSpace> getPixels(BufferedImage image, int image_width, int image_height){
        ArrayList<RGBColorSpace> pixels = new ArrayList<RGBColorSpace>();

        for(int y = 0; y < image_height; y++) {
            for (int x = 0; x < image_width; x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                pixels.add(new RGBColorSpace(pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue()));
            }
        }

        return pixels;
    }

}
