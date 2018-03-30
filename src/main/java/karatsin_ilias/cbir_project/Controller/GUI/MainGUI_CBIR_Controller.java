package karatsin_ilias.cbir_project.Controller.GUI;

import karatsin_ilias.cbir_project.Controller.ImageComparator.ImageComparatorController;
import karatsin_ilias.cbir_project.DAO.Resources.ImageTransformer;
import karatsin_ilias.cbir_project.FeatureExtractor.Color_HistogramExtractor;
import karatsin_ilias.cbir_project.Controller.Database.DatabaseConnector;
import karatsin_ilias.cbir_project.Controller.Database.DatabaseInsertController;
import karatsin_ilias.cbir_project.DAO.Database.DatabaseSelect;
import karatsin_ilias.cbir_project.FeatureExtractor.SURF_DescriptorExtractor;
import karatsin_ilias.cbir_project.Model.ComparisonImage;
import karatsin_ilias.cbir_project.Model.SimilarImage;
import karatsin_ilias.cbir_project.View.CustomPanel;
import karatsin_ilias.cbir_project.View.CustomSimilarImagePanel;
import karatsin_ilias.cbir_project.View.MainGUI_CBIR;
import org.opencv.core.MatOfKeyPoint;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class MainGUI_CBIR_Controller {


    private File currentSelectedImage = null;
    private MainGUI_CBIR mainGUI_cbir;
    private JList distanceMethods;
    private JPanel mainPanel;
    private JButton chooseImages;
    private JButton buttonCancel;
    private JRadioButton SURFButton;
    private JRadioButton RGBButton;
    private static String selectedDescriptor;
    private CustomPanel customImagePanelContainer;
    private JButton selectImageButton;
    private JButton findSimilarImagesButton;
    public static JTextField kneighborTxt;
    private CustomSimilarImagePanel customSimilarImagePanel;

    public MainGUI_CBIR_Controller(){

        initComponents();
        initListeners();

    }

    public void showMainGUI_CBIR_Window(){
        mainGUI_cbir.setVisible(true);
    }


    private void initComponents() {
        selectedDescriptor = "SURF";
        mainGUI_cbir = new MainGUI_CBIR();
        mainPanel = mainGUI_cbir.getMainPanel();
        chooseImages = mainGUI_cbir.getChooseImagesButton();
        buttonCancel = mainGUI_cbir.getCancelButton();
        SURFButton = mainGUI_cbir.getSURFButton();
        RGBButton = mainGUI_cbir.getRGBButton();
        distanceMethods = mainGUI_cbir.getDistanceMethods();

        customImagePanelContainer = mainGUI_cbir.getCustomImagePanelContainer();
        customSimilarImagePanel = mainGUI_cbir.getCustomSimilarImagePanel();

        selectImageButton = mainGUI_cbir.getSelectImageButton();
        findSimilarImagesButton = mainGUI_cbir.getFindSimilarImagesButton();
        kneighborTxt = mainGUI_cbir.getKneighborTxt();


        ButtonGroup editableGroup = new ButtonGroup();
        editableGroup.add(SURFButton);
        editableGroup.add(RGBButton);
    }



    private void initListeners(){
        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected: " + e.getActionCommand());
                File selectedFile = chooseFile();
                Image image = null;
                try {
                    currentSelectedImage = selectedFile;
                    BufferedImage bufferedImage = ImageIO.read(selectedFile);
                    image = bufferedImage.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
                    if(image!=null)
                        customImagePanelContainer.setImage(image);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });

        findSimilarImagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO here i get the descriptor out of selectedImage, and Knn search similar images in database
                String distanceMethod = (String) distanceMethods.getSelectedValue();
                ImageComparatorController imageComparatorController = new ImageComparatorController(currentSelectedImage,selectedDescriptor,getKNeighbor());
                List<ComparisonImage> similarImages = imageComparatorController.getComparisonResult(distanceMethod);

                List<SimilarImage> images = new ArrayList<SimilarImage>();

                try {
                    int counter = 1;
                    for (ComparisonImage currentImage : similarImages){
                        Image image = null;
                        String filePath = currentImage.getFilePath();
                        File file = new File(filePath);
                        BufferedImage bufferedImage = ImageIO.read(file);
                        image = bufferedImage.getScaledInstance(100, 100, Image.SCALE_DEFAULT);

                        if(currentImage!=null){
                            SimilarImage similarImage = new SimilarImage("\t"+Integer.toString(counter),image);
                            counter+=1;
                            images.add(similarImage);
                        }
                    }

                    customSimilarImagePanel.setImages(images);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                images = null;
            }
        });

        chooseImages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFiles(selectedDescriptor);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitProgram();
            }
        });

        SURFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton aButton = (AbstractButton) e.getSource();
                System.out.println("Selected: " + aButton.getText());
                selectedDescriptor = "SURF";

            }
        });

        RGBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton aButton = (AbstractButton) e.getSource();
                System.out.println("Selected: " + aButton.getText());
                selectedDescriptor = "RGB";

            }
        });

    }

    private File chooseFile(){
        File file = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.showOpenDialog(mainPanel);
        file = chooser.getSelectedFile();

        return file;
    }

    private void chooseFiles(String descriptorName){

        DatabaseInsertController databaseInsertController = new DatabaseInsertController();

        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.showOpenDialog(mainPanel);

        File[] files = chooser.getSelectedFiles();
        System.out.println("Files Selected!");

        ImageTransformer.resizeAndSaveImages(files);

        for (File file : files){
            if(descriptorName.equals("SURF")){
                insertSurfToDatabase(file,databaseInsertController);
            } else if(descriptorName.equals("RGB")) {
                insertHistogramToDatabase(file,databaseInsertController);
            }

        }

        DatabaseConnector.closeConnectionToDatabase();
    }



    private void exitProgram(){
        System.exit(0);
    }


    private void insertSurfToDatabase(File file,DatabaseInsertController databaseInsertController){
        Connection connection = null;
        String fileName = file.getName();
        String filePath = file.getAbsolutePath();
        Boolean databaseConnected = false;

        try {
            connection = DatabaseConnector.getPostGreSqlConn();
            databaseConnected = true;
        } catch (SQLException e) {
            databaseConnected = false;
            e.printStackTrace();
        }

        DatabaseSelect getFromDatabase = new DatabaseSelect();
        Boolean fileExistence = false;
        try {
            fileExistence = getFromDatabase.imageExistInSURFTable(connection, fileName, filePath);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!fileExistence && databaseConnected){
            SURF_DescriptorExtractor surf_descriptorExtractor = new SURF_DescriptorExtractor();
            String resizedImageResourcesPath = "src//resources//images//"+fileName;
            MatOfKeyPoint currentFileMatKeyPoint = surf_descriptorExtractor.getImageDescriptorMatFromFilename(resizedImageResourcesPath);

            surf_descriptorExtractor.initDataForDatabase(currentFileMatKeyPoint);

            databaseInsertController.insertDescriptorToDatabase(connection,"surf_descriptor",
                    fileName,filePath,surf_descriptorExtractor.getBlob(),surf_descriptorExtractor.getRows(),
                    surf_descriptorExtractor.getColumns(),surf_descriptorExtractor.getMattype());
        } else {
            System.out.println("File Surf Descriptor Vector Already in Database");
        }

    }

    private void insertHistogramToDatabase(File file,DatabaseInsertController databaseInsertController){
        Connection connection = null;
        BufferedImage img = null;
        String fileName = file.getName();
        String filePath = file.getAbsolutePath();
        Boolean databaseConnected = false;

        try {
            connection = DatabaseConnector.getPostGreSqlConn();
            databaseConnected = true;
        } catch (SQLException e) {
            databaseConnected = false;
            e.printStackTrace();
        }

        DatabaseSelect getFromDatabase = new DatabaseSelect();
        Boolean fileExistence = false;
        try {
            fileExistence = getFromDatabase.imageExistInHistogramTable(connection, fileName, filePath);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!fileExistence && databaseConnected){


            Color_HistogramExtractor colorHistogramCreator = new Color_HistogramExtractor();
            String resizedImageResourcesPath = "src//resources//images//"+fileName;
            try {
                img = ImageIO.read(new File(resizedImageResourcesPath));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            ArrayList<int[]> histogram = colorHistogramCreator.createImageHistogram(img);
            databaseInsertController.insertHistogramToDatabase(connection,fileName,filePath,histogram);

        } else {
            System.out.println("File Histogram Already in Database");
        }
    }

    public int getKNeighbor(){
        int result = 0;
        String kneighborText = kneighborTxt.getText();

        if(!Objects.equals(kneighborText, "")){
            try{
                result = Integer.parseInt(kneighborText);
            } catch (Exception ex){
                System.out.print(ex);
            }

        }

        return result;
    }

}
