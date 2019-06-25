package enhancer.colorspaces;

import enhancer.filehandlers.PNGReader;

import java.util.Arrays;
import java.util.List;

public class RGBColorSpace {
    private int red;
    private int green;
    private int blue;

    public RGBColorSpace(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public RGBColorSpace(String creationString){
        String tempString = creationString.replaceAll("[^0-9]+", " ");
        List<String> rgb = Arrays.asList(tempString.trim().split(" "));
        this.red = Integer.parseInt(rgb.get(0));
        this.green = Integer.parseInt(rgb.get(1));
        this.blue = Integer.parseInt(rgb.get(2));
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RGBColorSpace){
            RGBColorSpace tryObj = (RGBColorSpace)obj;
            return tryObj.red == this.red && tryObj.green == this.green && tryObj.blue == this.blue;
        }
        return false;
    }

    public String toString() { return "(" + this.red + ", " + this.green + ", " + this.blue + ')'; }
}
