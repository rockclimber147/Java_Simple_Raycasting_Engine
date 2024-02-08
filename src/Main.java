import model.Model;

import java.awt.*;

public class Main {

    private static final int CORRECTION_FACTOR = 100;
    private final Model gameModel;
    private final GameDisplay display;

    public Main(Dimension size){
        double correctedWidth = size.width - CORRECTION_FACTOR;
        double correctedHeight = size.height - CORRECTION_FACTOR;
        gameModel = new Model(correctedWidth, correctedHeight);
        display = new GameDisplay((int) correctedWidth, (int) correctedHeight, gameModel);
    }
    public static void main(String[] args) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println(size);
        new Main(size);
    }
}
