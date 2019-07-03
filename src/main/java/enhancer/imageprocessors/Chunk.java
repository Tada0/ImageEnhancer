package enhancer.imageprocessors;

import enhancer.colorspaces.RGBColorSpace;
import enhancer.filehandlers.PNGRepresentation;

import java.util.List;

class Chunk {

    private int size;

    private List<RGBColorSpace> pixels;

    Chunk(PNGRepresentation pngRepresentation, int size) {
        this.size = size;
        pixels = pngRepresentation.getPixels();
    }

    List<RGBColorSpace> getRow(int row) {
        return pixels.subList(row * size, (row + 1) * size);
    }


    public List<RGBColorSpace> getPixels() {
        return pixels;
    }

    public void setPixels(List<RGBColorSpace> pixels) {
        this.pixels = pixels;
    }
}
