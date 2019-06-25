package enhancer.imageprocessors;

import enhancer.colorspaces.RGBColorSpace;
import enhancer.filehandlers.PNGReader;
import enhancer.filehandlers.PNGRepresentation;
import org.assertj.core.api.Assertions;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class ImageComposerTest {

    @Test
    public void testImageComposerGetNewPixels(){
        // given
        String workingDirectory = System.getProperty("user.dir");
        String imagePath = workingDirectory + "\\assets\\6.png";
        String dataFilePath = workingDirectory + "\\assets\\ClassificationFiles\\6";
        String referenceImagesDirectory = workingDirectory + "\\assets\\ReferenceImagesPNG\\";
        String referenceDataFilePath = workingDirectory + "\\assets\\ClassificationFiles\\referenceClassificationFile";
        String referenceImagePath = workingDirectory + "\\assets\\6target.png";
        PNGRepresentation image = PNGReader.read(imagePath);
        PNGRepresentation referenceImage = PNGReader.read(referenceImagePath);
        Map<Integer, String> imageChangeMap = new ImageComparator().getChangeMap(dataFilePath, referenceDataFilePath);
        ImageComposer imageComposer = new ImageComposer();

        //when
        List<RGBColorSpace> newPixels =  imageComposer.getNewPixels(image, referenceImagesDirectory, imageChangeMap, 32);

        //then
        Assertions.assertThat(newPixels).containsExactly(referenceImage.getPixels().toArray(new RGBColorSpace[0]));

    }

}