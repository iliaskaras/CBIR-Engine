package karatsin_ilias.cbir_project.Controller.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageEditor {

    public static BufferedImage resizeImage(BufferedImage originalImage){

        int type = getImageType(originalImage);

        BufferedImage resizedImage = new BufferedImage(300, 300, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 300, 300, null);
        g.dispose();

        return resizedImage;

    }

    public static int getImageType(BufferedImage image){

        int type = -1;

        type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();

        return type;

    }


}
