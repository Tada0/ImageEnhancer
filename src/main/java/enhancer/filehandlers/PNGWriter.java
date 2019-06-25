package enhancer.filehandlers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class PNGWriter {

    public static void write(PNGRepresentation image, String filepath){
        byte[] pixelByteArray = createByteArray(image);
        DataBuffer dataBuffer = new DataBufferByte(pixelByteArray, pixelByteArray.length);
        WritableRaster writableRaster = Raster.createInterleavedRaster(dataBuffer, image.getWidth(), image.getHeight(), 3 * image.getWidth(), 3, new int[] {0, 1, 2}, (Point)null);
        ColorModel colorModel = new ComponentColorModel(ColorModel.getRGBdefault().getColorSpace(), false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        BufferedImage bufferedImage = new BufferedImage(colorModel, writableRaster, true, null);
        overrideFile(bufferedImage, filepath);
    }

    private static byte[] createByteArray(PNGRepresentation image){
        byte[] pixelByteArray = new byte[image.getPixels().size() * 3];

        for(int i = 0; i < image.getPixels().size(); i++){
            pixelByteArray[(i*3)] = (byte)image.getPixels().get(i).getRed();
            pixelByteArray[(i*3)+1] = (byte)image.getPixels().get(i).getGreen();
            pixelByteArray[(i*3)+2] = (byte)image.getPixels().get(i).getBlue();
        }

        return pixelByteArray;
    }

    private static void overrideFile(BufferedImage bufferedImage, String filepath){
        try {
            ImageIO.write(bufferedImage, "png", new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
