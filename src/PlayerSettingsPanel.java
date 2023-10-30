import model.Model;
import model.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerSettingsPanel extends JPanel implements ChangeListener, ActionListener {

    JSlider rayCountSlider;
    JSlider FOVSlider;
    JSlider scaleSlider;
    JSlider colorFactorSlider;
    private final Player player;
    private final Model model;
    JLabel rayCountLabel;
    JLabel FOVLabel;
    JLabel scaleLabel;
    JLabel colorFactorLabel;

    public PlayerSettingsPanel(Model model){

        this.setPreferredSize(new Dimension(GameDisplay.mainScreenWidth / 2, GameDisplay.mainScreenHeight / 2));
        this.setBackground(Color.DARK_GRAY);
        this.model = model;
        this.player = model.getPlayer();

        rayCountSlider = new JSlider(1,500,1);
        FOVSlider = new JSlider(1,360,90);
        scaleSlider = new JSlider(100,50000,25000);
        colorFactorSlider = new JSlider(10,1000,500);

        rayCountLabel = new JLabel("Rays: " + rayCountSlider.getValue());
        rayCountLabel.setPreferredSize(new Dimension(200,GameDisplay.mainScreenHeight/20));
        rayCountLabel.setOpaque(true);
        this.add(rayCountLabel);

        rayCountSlider.addChangeListener(this);
        rayCountSlider.setPreferredSize(new Dimension (GameDisplay.mainScreenWidth / 2 - 250,GameDisplay.mainScreenHeight / 20));
        rayCountSlider.setPaintLabels(true);
        rayCountSlider.setMajorTickSpacing(30);
        rayCountSlider.setMinorTickSpacing(5);
        rayCountSlider.setVisible(true);
        this.add(rayCountSlider);

        FOVLabel = new JLabel("FOV: 90");
        FOVLabel.setPreferredSize(new Dimension(200,GameDisplay.mainScreenHeight/20));
        FOVLabel.setOpaque(true);
        this.add(FOVLabel);

        FOVSlider.addChangeListener(this);
        FOVSlider.setPreferredSize(new Dimension (GameDisplay.mainScreenWidth / 2 - 250,GameDisplay.mainScreenHeight / 20));
        FOVSlider.setVisible(true);
        this.add(FOVSlider);

        scaleLabel = new JLabel("Scale Factor: " + scaleSlider.getValue());
        scaleLabel.setPreferredSize(new Dimension(200,GameDisplay.mainScreenHeight/20));
        scaleLabel.setOpaque(true);
        this.add(scaleLabel);

        scaleSlider.addChangeListener(this);
        scaleSlider.setPreferredSize(new Dimension (GameDisplay.mainScreenWidth / 2 - 250,GameDisplay.mainScreenHeight / 20));
        scaleSlider.setVisible(true);
        this.add(scaleSlider);

        colorFactorLabel = new JLabel("Color Factor: " + scaleSlider.getValue());
        colorFactorLabel.setPreferredSize(new Dimension(200,GameDisplay.mainScreenHeight/20));
        colorFactorLabel.setOpaque(true);
        this.add(colorFactorLabel);

        colorFactorSlider.addChangeListener(this);
        colorFactorSlider.setPreferredSize(new Dimension (GameDisplay.mainScreenWidth / 2 - 250,GameDisplay.mainScreenHeight / 20));
        colorFactorSlider.setVisible(true);
        this.add(colorFactorSlider);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch(e.getKeyChar()){
                    case 'q'-> player.rotateSelf(1);
                    case 'e'-> player.rotateSelf(-1);
                    case 'w'-> player.advance(1);
                    case 's' -> player.advance (-1);
                    case 'a' -> player.strafe(1);
                    case 'd' -> player.strafe(-1);
                }
                model.updateRayOrigin();
                model.updateAngles();
                model.castRays();
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        this.setFocusable(true);
        this.setOpaque(true);
        this.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == rayCountSlider) {
            model.changeRayCount(rayCountSlider.getValue());
            rayCountLabel.setText("Rays: " + rayCountSlider.getValue());
        } else if (e.getSource() == FOVSlider){
            model.changeFOV(FOVSlider.getValue());
            FOVLabel.setText("FOV: " + FOVSlider.getValue());
        } else if (e.getSource() == scaleSlider){
            model.setRectangleScalingFactor(scaleSlider.getValue());
            scaleLabel.setText("Scale Factor: " + scaleSlider.getValue());
        } else if (e.getSource() == colorFactorSlider){
            model.setColorFactor(colorFactorSlider.getValue());
            colorFactorLabel.setText("Color Factor: " + colorFactorSlider.getValue());
        }
        model.updateAngles();
        model.castRays();
        this.requestFocusInWindow(); // make focus on settings frame again so keyboard input works
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
