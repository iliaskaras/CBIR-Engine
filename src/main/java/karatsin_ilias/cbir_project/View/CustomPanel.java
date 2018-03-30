package karatsin_ilias.cbir_project.View;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel
{
    private Image image;



    public CustomPanel()
    {
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

    }

    @Override
    public Dimension getPreferredSize()
    {
        return (new Dimension(300, 300));

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public void setImage(Image image){
        this.image = image;
        paintComponent(getGraphics());
    }
}
