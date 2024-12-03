package main;
import entity.*;
import entity.Inventory;
import entity.TileDistanceDraw;
import entity.TileDraw;
import mapGeneration.FirstMapGeneration;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public static java.util.List<Integer> tileLocations = new ArrayList<>();

    private Thread thread;
    private Player player;
    private Inventory inventory;
    private TileDraw tileDraw;
    private TileDistanceDraw tileDistanceDraw;

    public static int spacesCrossed = 0; //This is how many spaces the player has crossed
    private final int originalTileSize = 23;
    private final double scale = 2.0; //Adjust scale from 3 to 2.0 for 1.5x smaller tiles
    public final int tileSize = (int) (originalTileSize * scale);
    public final int screenWidth = tileSize * 11; //Keep these the same as before
    public final int screenHeight = tileSize * 17;

    public GamePanel() throws IOException, FontFormatException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.LIGHT_GRAY);
        this.setFocusable(true);
        startup();
    }

    private void startup() throws IOException, FontFormatException {
        FirstMapGeneration firstMapGeneration = new FirstMapGeneration(this);
        firstMapGeneration.generateFirstMap();

        inventory = new Inventory(this);
        tileDraw = new TileDraw(this);
        tileDistanceDraw = new TileDistanceDraw(this);

        player = new Player(this);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        int FPS = 60;
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (thread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            tileDraw.draw((Graphics2D) g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player.draw((Graphics2D) g);
        tileDistanceDraw.draw((Graphics2D) g);
        inventory.draw((Graphics2D) g);
    }
}
