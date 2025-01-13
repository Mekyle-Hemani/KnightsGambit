package main;
import collision.ChestAccess;
import entity.*;
import inventory.*;
import mapDevelopmentFunctions.*;
import mapGeneration.*;
import tileDrawing.*;
import saveFunction.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    public static java.util.List<Integer> tileLocations = new ArrayList<>();

    private Thread thread;
    private Player player;
    private Inventory inventory;
    private TileDraw tileDraw;
    private TileDistanceDraw tileDistanceDraw;
    private ChestAccess chestAccess;
    private CoinDraw coinDraw;

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

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 32));
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setForeground(Color.WHITE);
        startButton.setBounds((screenWidth - 150) / 2, (screenHeight - 50) / 2, 150, 50);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startup(false);
                } catch (IOException | FontFormatException ex) {
                    throw new RuntimeException(ex);
                }
                panel.setVisible(false);
            }
        });

        JButton loadButton = new JButton("Load");
        loadButton.setFont(new Font("Arial", Font.BOLD, 32));
        loadButton.setBackground(Color.DARK_GRAY);
        loadButton.setForeground(Color.WHITE);
        loadButton.setBounds((screenWidth - 150) / 2, (screenHeight - 50) / 2 + (screenHeight / 4), 150, 50);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startup(true);
                } catch (IOException | FontFormatException ex) {
                    throw new RuntimeException(ex);
                }
                panel.setVisible(false);
            }
        });

        panel.add(startButton);
        panel.add(loadButton);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);

        this.setVisible(true);
        panel.revalidate();
        panel.repaint();
    }


    //These are all the different functions the game will do before anything else starts
    private void startup(boolean loadSave) throws IOException, FontFormatException {
        FirstMapGeneration firstMapGeneration = new FirstMapGeneration(this); //Allows functions from this specific class to be called by initiating it
        firstMapGeneration.generateFirstMap(); //Runs a specified script to initiate certain parts of the game

        NextLineGeneration nextLineGeneration = new NextLineGeneration(this); //Allows functions from this specific class to be called by initiating it
        nextLineGeneration.setup(); //Runs a specified script to initiate certain parts of the game

        TreeLocationGeneration treeLocationGeneration = new TreeLocationGeneration(this); //Allows functions from this specific class to be called by initiating it
        treeLocationGeneration.setup(); //Runs a specified script to initiate certain parts of the game

        EntranceLocationGeneration entranceLocationGeneration = new EntranceLocationGeneration(this); //Allows functions from this specific class to be called by initiating it
        EntranceLocationGeneration.setup(); //Runs a specified script to initiate certain parts of the game

        BridgeLocationGeneration bridgeLocationGeneration = new BridgeLocationGeneration(this); //Allows functions from this specific class to be called by initiating it
        BridgeLocationGeneration.setup(); //Runs a specified script to initiate certain parts of the game

        saveRegion saveRegionGeneration = new saveRegion(this); //Allows functions from this specific class to be called by initiating it
        saveRegionGeneration.setup(); //Runs a specified script to initiate certain parts of the game

        ChestAccess chestAccess = new ChestAccess(this); //Allows functions from this specific class to be called by initiating it
        chestAccess.setup(); //Runs a specified script to initiate certain parts of the game

        CoinDraw coinDraw = new CoinDraw(this);

        //Starts to draw the rest of the visual items after necessary initialization
        inventory = new Inventory(this);
        tileDraw = new TileDraw(this);
        tileDistanceDraw = new TileDistanceDraw(this);
        player = new Player(this);

        if (loadSave) {
            System.out.println("Loading old save");
            java.util.List<Integer> result = new ArrayList<>();
            ArrayList<String> input = (ArrayList<String>) save.load("tiles.txt");

            String[] parts = input.get(0).replace("[", "").replace("]", "").split(",\\s*");

            ArrayList<Integer> integerList = new ArrayList<>();
            for (String part : parts) {
                integerList.add(Integer.parseInt(part));
            }

            System.out.println(integerList);
            NextLineGeneration.nextTileLocations = integerList;
        }

        //Starts the actual game
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

    //This draws every new frame
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //This is in a try catch as the script needs to have some sort of fail-safe if the wanted pngs are not present
        try {
            tileDraw.draw((Graphics2D) g); //Draw every tile that is supposed to be shown onscreen
        } catch (IOException e) {
            throw new RuntimeException(e); //Throw possible errors
        }

        player.draw((Graphics2D) g); //Draw the player
        tileDistanceDraw.draw((Graphics2D) g); //Draw the score
        inventory.draw((Graphics2D) g); //Draw the hidden inventory
    }
}
