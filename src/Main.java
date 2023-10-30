import model.Model;

import java.awt.*;

public class Main {
    private final Model gameModel;
    private final GameDisplay display;

    public Main(Dimension size){
        double correctedWidth = size.width - 50;
        double correctedHeight = size.height - 50;
        gameModel = new Model(correctedWidth, correctedHeight);
        display = new GameDisplay((int) correctedWidth, (int) correctedHeight, gameModel);
    }
    public static void main(String[] args) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(size);
        new Main(size);
    }
}
