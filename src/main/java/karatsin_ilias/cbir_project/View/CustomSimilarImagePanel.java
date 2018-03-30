package karatsin_ilias.cbir_project.View;

import karatsin_ilias.cbir_project.Model.SimilarImage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomSimilarImagePanel extends JPanel {

    private List<SimilarImage> images = null;

    private static final int imageWidth = 100;
    private static final int imageHeight = 100;

    public CustomSimilarImagePanel()
    {
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

    }


    @Override
    public Dimension getPreferredSize()
    {
        return (new Dimension(300, 5000));

    }



    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int x = 0;
        int y = 0;
        int lineCounter = 0;
        int padding = 10;

        if(images!=null){
            for (SimilarImage image : images){
                g.drawImage(image.getFileName(), x+padding, y, this);
                g.drawString(image.getImageName(), x+padding, y+115);
                lineCounter += 1;
                padding += 10;
                x += 100;
                if(lineCounter==3){
                    x = 0;
                    y += 130;
                    lineCounter = 0;
                    padding = 10;
                }
            }
        }



    }

    public void setImages(List<SimilarImage> images){
        this.images = images;
        paintComponent(getGraphics());
    }
}
