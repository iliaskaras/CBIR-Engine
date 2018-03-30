package karatsin_ilias.cbir_project.DAO.Resources;

import karatsin_ilias.cbir_project.Controller.Resources.ImageEditor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTransformer {

    public static void resizeAndSaveImages(File[] files){

        for (File file : files){
            try {
                resizeAndSaveJPGImage(file.getName(),file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void resizeAndSaveJPGImage(String fileName, String filePath) throws IOException {

        File file = new File(filePath);
        BufferedImage originalImage = null;

        try {
            originalImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert originalImage != null;

        BufferedImage resizeImageJpg = ImageEditor.resizeImage(originalImage);
        ImageIO.write(resizeImageJpg, "jpg", new File("src//resources//images//"+fileName));

    }


}
