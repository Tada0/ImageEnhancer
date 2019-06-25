package enhancer.imageprocessors;

import enhancer.colorspaces.RGBColorSpace;

import java.util.ArrayList;
import java.util.List;

class Row {

    private List<RGBColorSpace> pixels;

    Row(List<Chunk> chunks, int row) {
        pixels = new ArrayList<>();
        chunks.forEach(chunk -> pixels.addAll(chunk.getRow(row)));
    }

    public List<RGBColorSpace> getPixels() {
        return pixels;
    }

    public void setPixels(List<RGBColorSpace> pixels) {
        this.pixels = pixels;
    }
}
