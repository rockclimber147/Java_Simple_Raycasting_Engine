import model.Model;
import model.Player;
import vectors.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


public class GameDisplay extends JFrame implements ChangeListener{
    JLayeredPane gameWindow;
    PlayerDisplayPanel topDown;
    PlayerSettingsPanel settings;
    PlayerViewPanel playerView;
    GridBagConstraints c;
    Model model;
    static int mainScreenHeight;
    static int mainScreenWidth;

    public GameDisplay(int mainScreenWidth, int mainScreenHeight, Model model) {
        GameDisplay.mainScreenHeight = mainScreenHeight;
        GameDisplay.mainScreenWidth = mainScreenWidth;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        this.playerView = new PlayerViewPanel(model);
        this.topDown = new PlayerDisplayPanel(model);
        this.settings = new PlayerSettingsPanel(model);
        this.gameWindow = new JLayeredPane();


        gameWindow.setPreferredSize(new Dimension(mainScreenWidth, mainScreenHeight));
        gameWindow.setBackground(Color.PINK);
        gameWindow.setLayout(new GridBagLayout());


        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        gameWindow.add(playerView, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        gameWindow.add(topDown, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        gameWindow.add(settings, c);

        gameWindow.setOpaque(true);
        gameWindow.setVisible(true);
        this.add(gameWindow);

        this.pack();
        this.setVisible(true);
    }

    public void loadModel(Model model){
        this.model = model;
    }
    public Model getModel(){
        return this.model;
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
