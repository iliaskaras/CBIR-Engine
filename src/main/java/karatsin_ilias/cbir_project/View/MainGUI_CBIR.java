package karatsin_ilias.cbir_project.View;

import javax.swing.*;

public class MainGUI_CBIR extends JFrame{

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    private JPanel mainPanel;
    private JPanel contentPane;
    private JButton chooseImagesButton;
    private JButton cancelButton;
    private JRadioButton SURFButton;
    private JRadioButton RGBButton;
    private JLabel chooseImagesLabel;
    private JButton selectImageButton;
    private JPanel selectedImagePanel;
    private JButton findSimilarImagesButton;
    private JTextField kneighborTxt;
    private JList distanceMethods;
    private JPanel similarImagesPanel;
    private JLabel ChooseDistanceMethod;
    private CustomPanel customImagePanelContainer;
    private CustomSimilarImagePanel customSimilarImagePanel;

    public MainGUI_CBIR(){

        setSize(WIDTH,HEIGHT);
        setContentPane(mainPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        customImagePanelContainer = new CustomPanel();
        selectedImagePanel.add(customImagePanelContainer);

        customSimilarImagePanel = new CustomSimilarImagePanel();
        JScrollPane scrollFrame = new JScrollPane(customSimilarImagePanel);
        similarImagesPanel.add(scrollFrame);

    }

    public CustomSimilarImagePanel getCustomSimilarImagePanel() {
        return customSimilarImagePanel;
    }

    public JList getDistanceMethods() {
        return distanceMethods;
    }

    public CustomPanel getCustomImagePanelContainer() {
        return customImagePanelContainer;
    }

    public JButton getFindSimilarImagesButton() {
        return findSimilarImagesButton;
    }

    public JButton getChooseImagesButton() {
        return chooseImagesButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JRadioButton getSURFButton() {
        return SURFButton;
    }

    public JRadioButton getRGBButton() {
        return RGBButton;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    public JButton getSelectImageButton() {
        return selectImageButton;
    }

    public JTextField getKneighborTxt() {
        return kneighborTxt;
    }

}
