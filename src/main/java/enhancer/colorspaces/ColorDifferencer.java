package enhancer.colorspaces;

import java.util.function.DoubleFunction;

public class ColorDifferencer {

    private CIEXYZColorSpace rgbToCiexyz(RGBColorSpace rgbColor){

        DoubleFunction<Double> f = x -> x > 0.04045 ? (Math.pow(((x + 0.055) / 1.055), 2.4)) * 100.0 : (x / 12.92) * 100.0;

        double red = f.apply(rgbColor.getRed() / 255.0);
        double green = f.apply(rgbColor.getGreen() / 255.0);
        double blue = f.apply(rgbColor.getBlue() / 255.0);

        double x = red * 0.4124 + green * 0.3576 + blue * 0.1805;
        double y = red * 0.2126 + green * 0.7152 + blue * 0.0722;
        double z = red * 0.0193 + green * 0.1192 + blue * 0.9505;

        return new CIEXYZColorSpace(x, y, z);

    }

    private CIELABColorSpace ciexyzToCielab(CIEXYZColorSpace color){
        //AdobeRGB ColorSpace

        DoubleFunction<Double> f = x -> x > 0.008856 ?  Math.pow(x, (1.0/3.0)) : (7.787 * x) + (16.0 / 116.0);

        double x = f.apply(color.getX() / 94.811);
        double y = f.apply(color.getY() / 100.000);
        double z = f.apply(color.getZ() / 107.304);

        double l = (116.0 * y) - 16.0;
        double a = 500.0 * (x - y);
        double b = 200.0 * (y - z);

        return new CIELABColorSpace(l, a, b);

    }

    private double dE94(CIELABColorSpace color1, CIELABColorSpace color2){
        double dL = color1.getL() - color2.getL();
        double c1 = Math.sqrt(color1.getA() * color1.getA() + color1.getB() * color1.getB());
        double c2 = Math.sqrt(color2.getA() * color2.getA() + color2.getB() * color2.getB());
        double cab = c1 - c2;
        double da = (color1.getA() - color2.getA()) * (color1.getA() - color2.getA());
        double db = (color1.getB() - color2.getB()) * (color1.getB() - color2.getB());
        double hab = da + db - (cab * cab);
        hab = hab > 0.0 ? Math.sqrt(hab) : 0.0;

        double sl = 1.0;
        double sc = 1.0 + 0.045 * c1;
        double sh = 1.0 + 0.015 * c1;

        return Math.sqrt((dL/sl) * (dL/sl) + (cab/sc) * (cab/sc) + (hab/sh) * (hab/sh));

     }

     public double getDifference(RGBColorSpace color1, RGBColorSpace color2){
        ColorDifferencer colorDifferencer = new ColorDifferencer();
        CIELABColorSpace cielabColor1 = colorDifferencer.ciexyzToCielab(rgbToCiexyz(color1));
        CIELABColorSpace cielabColor2 = colorDifferencer.ciexyzToCielab(rgbToCiexyz(color2));
        return colorDifferencer.dE94(cielabColor1, cielabColor2);
     }

}
