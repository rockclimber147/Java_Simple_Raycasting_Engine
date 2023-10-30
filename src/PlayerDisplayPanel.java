import javax.swing.*;

import model.GameMap;
import model.Model;
import model.Player;
import model.Polygon;
import vectors.LineSegment;
import vectors.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerDisplayPanel extends JPanel {

    Player player;
    int[][][] map;
    Model model;

    public PlayerDisplayPanel(Model model){
        this.setPreferredSize(new Dimension(GameDisplay.mainScreenWidth / 2, GameDisplay.mainScreenHeight / 2));
        this.setBackground(Color.DARK_GRAY);
        this.player = model.getPlayer();
        this.model = model;
        map = model.getGameMap().exportPolygons();

        this.setOpaque(true);
        this.setVisible(true);
    }

    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(10));

        paintMap(g2d);

        drawColoredPolygonBorders(g2d);

        paintRays(g2d);

        g2d.setColor(Color.black);
        g2d.drawPolygon(player.getXArray(), player.getYArray(), 4);
        g2d.setColor(Color.blue);
        g2d.fillPolygon(player.getXArray(), player.getYArray(), 4);


        this.getRootPane().repaint();
        this.getRootPane().setVisible(true);
    }

    private void paintMap(Graphics2D g){
        if (map == null){
            return;
        }
        g.setColor(Color.darkGray);
        g.fillPolygon(map[0][0], map[0][1], map[0][0].length);

        g.setColor(Color.black);

        if (map.length > 1){
            for (int i = 1; i < map.length; i++) {
                g.fillPolygon(map[i][0], map[i][1], map[i][0].length);
                g.setStroke(new BasicStroke(2));
            }
        }
    }
    private void drawColoredPolygonBorders(Graphics2D g){
        g.setStroke(new BasicStroke(5));
        double[] c;
        Vector start;
        Vector end;
        ArrayList<LineSegment> mapEdges = model.getMapEdges();
        for (LineSegment l:mapEdges){
            c = l.getColor();
            start = l.getStartPoint();
            end = l.getEndPoint();
            g.setColor(new Color((int) c[0], (int) c[1], (int) c[2]));
            g.drawLine((int) start.getX(),
                    (int) start.getY(),
                    (int) end.getX(),
                    (int) end.getY());
        }
    }

    private void paintRays(Graphics2D g2d){

        int originX = (int) player.getPosition().getX();
        int originY = (int) player.getPosition().getY();
        int[][] formattedRays = model.getFormattedRays();

        g2d.setColor(Color.yellow);
        g2d.setStroke(new BasicStroke(1));

        for (int[] ray:formattedRays){
            g2d.drawLine(originX,originY,ray[0],ray[1]);
        }

    }
}
