import model.Model;
import model.ModelData;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerViewPanel extends JPanel {

    JLabel label;
    private final Model model;
    int height;
    int width;
    GradientPaint topGradient;
    GradientPaint bottomGradient;
    ArrayList<ModelData> rayData;

    PlayerViewPanel(Model model){
        this.model = model;
        this.rayData =  model.getRayData();
        this.width = GameDisplay.mainScreenWidth;
        this.height = GameDisplay.mainScreenHeight / 2;
        this.topGradient = new GradientPaint(0,0,new Color(225,225,225),0,height / 2,new Color(20,20,20));
        this.bottomGradient = new GradientPaint(0,height / 2,new Color(20,20,20),0,height,new Color(225,225,225));
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.LIGHT_GRAY);

        label = new JLabel("First Person View");
        this.add(label);

        this.setOpaque(true);
        this.setVisible(true);
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(topGradient);
        g2d.fill(new Rectangle(0,0,width,height/2));
        g2d.setPaint(bottomGradient);
        g2d.fill(new Rectangle(0,height/2,width,height));


        //renderAsColoredRectangles(g2d, model.getRectangleInfo());

        renderAsColoredRectanglesFromData(g2d, model.getRayData());

        this.getRootPane().repaint();
        this.getRootPane().setVisible(true);
    }

    private void renderAsRectangles(Graphics2D g2d, int[][] rectangleInfo){
        int[] rectangleHeights = rectangleInfo[1];
        int[] rectangleWidths = rectangleInfo[0];
        double[] distanceFactor = model.getDistanceShaders();
        int xOffset = 0;
        for (int i = 0; i < rectangleWidths.length; i++){
            int yOffset = (height - rectangleHeights[i]) / 2;
            g2d.setColor(new Color(0, 0, (int) distanceFactor[i]));
            g2d.fillRect(xOffset,yOffset,rectangleWidths[i],rectangleHeights[i]);

            xOffset += rectangleWidths[i];
        }
    }

    private void renderAsColoredRectangles(Graphics2D g2d, int[][] rectangleInfo){
        int[] rectangleHeights = rectangleInfo[1];
        int[] rectangleWidths = rectangleInfo[0];
        double[] colorCoefficients = model.getDistanceColorCoefficients();
        double[][] targetColors = model.getTargetColors();
        int xOffset = 0;
        for (int i = 0; i < rectangleWidths.length; i++){
            int yOffset = (height - rectangleHeights[i]) / 2;
            g2d.setColor(new Color((int) (colorCoefficients[i] * targetColors[i][0]),
                    (int) (colorCoefficients[i] * targetColors[i][1]),
                    (int) (colorCoefficients[i] * targetColors[i][2])));
            g2d.fillRect(xOffset,yOffset,rectangleWidths[i],rectangleHeights[i]);


            xOffset += rectangleWidths[i];
        }
    }

    private void renderAsColoredRectanglesFromData(Graphics2D g2d, List<ModelData> rayData){
        int xOffset = 0;
        for (int i = 0; i < model.getRayCount(); i++) {
            int yOffset = (height - rayData.get(i).rectangleHeight) / 2;

            double coefficient = rayData.get(i).colorCoefficient;

            g2d.setColor(new Color((int) (coefficient * rayData.get(i).targetColor[0]),
                    (int) (coefficient * rayData.get(i).targetColor[1]),
                    (int) (coefficient * rayData.get(i).targetColor[2])));
            g2d.fillRect(xOffset, yOffset, rayData.get(i).rectangleWidth, rayData.get(i).rectangleHeight);


            xOffset += rayData.get(i).rectangleWidth;
        }
    }

}
