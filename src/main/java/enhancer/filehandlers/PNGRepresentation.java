package enhancer.filehandlers;

import enhancer.colorspaces.RGBColorSpace;

import java.util.List;

public class PNGRepresentation {
    private int width;
    private int height;
    private List<RGBColorSpace> pixels;

    public PNGRepresentation(int width, int height, List<RGBColorSpace> pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public List<RGBColorSpace> getPixels() {
        return this.pixels;
    }

}
