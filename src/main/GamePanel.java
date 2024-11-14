package main;
import entity.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    private Thread thread;
    private final int FPS = 60;

    private Player player;

    public static java.util.List<Integer> tileLocations = new ArrayList<>();

    private Inventory inventory;
    private TileDraw tileDraw;
    private Collision collision;

    private NextLineGeneration nextLineGeneration;
    private FirstMapGeneration firstMapGeneration;

    private final int originalTileSize = 23;
    private final double scale = 2.0;  // Adjust scale from 3 to 2.0 for 1.5x smaller tiles
    public final int tileSize = (int) (originalTileSize * scale);
    public final int screenWidth = tileSize * 11;  // Keep these the same as before
    public final int screenHeight = tileSize * 17;

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.LIGHT_GRAY);
        this.setFocusable(true);
        startup();
    }

    private void startup() throws IOException {
        firstMapGeneration = new FirstMapGeneration(this);
        firstMapGeneration.generateFirstMap();


        inventory = new Inventory(this);
        tileDraw = new TileDraw(this);

        player = new Player(this);

        nextLineGeneration = new NextLineGeneration(this);
        //nextLineGeneration.generateNextLine();

        collision = new Collision(this);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
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
        inventory.draw((Graphics2D) g);
        try {
            tileDraw.draw((Graphics2D) g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        player.draw((Graphics2D) g);
    }
}