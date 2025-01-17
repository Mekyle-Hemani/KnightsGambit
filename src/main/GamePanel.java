package main;
import collision.ChestAccess;
import entity.*;
import inventory.*;
import mapDevelopmentFunctions.*;
import mapGeneration.*;
import movement.Movement;
import tileDrawing.*;
import saveFunction.*;
import dialog.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {
    public static java.util.List<Integer> tileLocations = new ArrayList<>();

    //These are all required to mention the setup script from their respective files
    private Thread thread;
    private Player player;
    private Inventory inventory;
    private TileDraw tileDraw;
    private TileDistanceDraw tileDistanceDraw;
    private DialogBox dialogBox;

    public static int spacesCrossed = 0; //This is how many spaces the player has crossed
    private static final int originalTileSize = 23;
    private static final double scale = 2.0; //Adjust scale from 3 to 2.0 for 1.5x smaller tiles
    public static final int tileSize = (int) (originalTileSize * scale); //This is the size of each tile that will be drawn
    public static final int screenWidth = tileSize * 11; //This is the width of the screen drawn
    public final int screenHeight = tileSize * 17; //This is the height of the screen drawn

    private final BufferedImage UiImage =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/assets/ui/uiBackground.png"))); //This is the background image for the UI

    public static boolean gameStarted = false; //This will change if the game is supposed to start

    private static boolean corruptSave = false; //This will change if there is something wrong with the save

    public GamePanel() throws IOException, FontFormatException {
        //This is the font of the title
        Font titleFont = loadCustomFont(48, Font.BOLD); //This sets up the custom font
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); //This sets up a UI with the exact screen dimensions
        this.setFocusable(true); //This allows the user to select it

        JLabel titleLabel = new JLabel("Knight's Gambit", SwingConstants.CENTER); //This is the label of the title
        titleLabel.setFont(titleFont); //This sets the font up
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(false);
        titleLabel.setBounds((screenWidth-600) / 2, 100, 600, 100);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 32));
        startButton.setBackground(Color.DARK_GRAY);
        startButton.setForeground(Color.WHITE);
        startButton.setBounds((screenWidth - 150) / 2, (screenHeight / 2) - 75, 150, 50);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startup(false);
                } catch (IOException | FontFormatException ex) {
                    throw new RuntimeException(ex);
                }
                panel.setVisible(false);
                titleLabel.setVisible(false);
            }
        });

        JButton loadButton = new JButton("Load");
        loadButton.setFont(new Font("Arial", Font.BOLD, 32));
        loadButton.setBackground(Color.DARK_GRAY);
        loadButton.setForeground(Color.WHITE);
        loadButton.setBounds((screenWidth - 150) / 2, (screenHeight / 2) + 130, 150, 50);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startup(true);
                } catch (IOException | FontFormatException ex) {
                    throw new RuntimeException(ex);
                }
                panel.setVisible(false);
                titleLabel.setVisible(false);
            }
        });

        panel.add(startButton);
        panel.add(loadButton);

        this.setLayout(null);
        this.add(titleLabel);
        this.add(panel);

        panel.setBounds(0, 0, screenWidth, screenHeight);
        this.setVisible(true);
        panel.revalidate();
        panel.repaint();
    }


    public static Font loadCustomFont(int size, int type) throws IOException, FontFormatException {
        InputStream fontStream = GamePanel.class.getResourceAsStream("/assets/font/scoreFont.ttf"); //Find the font file
        if (fontStream != null) { //If the file exists...
            return Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(type, size); //Set the font settings to be the parameters
        } else { //If not...
            return new Font("Arial", type, size); //Set the same settings but to Arial
        }
    }

    //These are all the different functions the game will do before anything else starts
    private void startup(boolean loadSave) throws IOException, FontFormatException {
        gameStarted = true;
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

        new CoinDraw(this);

        //Starts to draw the rest of the visual items after necessary initialization
        inventory = new Inventory(this);
        dialogBox = new DialogBox(this);
        tileDraw = new TileDraw(this);
        tileDistanceDraw = new TileDistanceDraw(this);
        player = new Player(this);

        //If the player wants to load a save
        if (loadSave) {
            convertSaveToArray("tiles.txt"); //This trys to load a save to see if it's not present
            if (corruptSave) { //If the save is not present
                DialogBox.dialogText = "Game Save Not Present. Starting New Save"; //Start a new save
            } else { //If the save is present
                //Change the map and inventory depending on the save
                NextLineGeneration.nextTileLocations = convertSaveToArray("tiles.txt");
                Movement.spacesCrossed = (convertSaveToArray("spaces.txt")).getFirst();

                DialogBox.dialogText = "Game Loaded";
            }
        } else { //If new game has been selected
            DialogBox.dialogText = "Game Started";
        }
        DialogBox.isVisible = true; //Make the dialog box visible

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

    public ArrayList<Integer> convertSaveToArray(String filename) throws IOException {
        ArrayList<String> input = (ArrayList<String>) save.load(filename, false); //Gets the save and assigns it to an arraylist
        ArrayList<Integer> result = new ArrayList<>(); //Creates a result arraylist
        String[] parts = input.getFirst().replace("[", "").replace("]", "").split(",\\s*"); //This removes all the special characters from the input array

        for (int i = parts.length - 1; i >= 0; i--) { //Counting backwards...
            String part = parts[i]; //Get each item of the input array
            if (Objects.equals(part, "Error loading file") || corruptSave){
                result.clear();
                corruptSave = true;
                return result;
            }
            result.add(Integer.parseInt(part)); //Convert it to a string and add it to the output array
        }

        return result; //Return the output array
    }

    //This draws every new frame
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(UiImage, 0, 0, screenWidth, screenHeight, this);
        if (gameStarted) {
            //This is in a try catch as the script needs to have some sort of fail-safe if the wanted pngs are not present
            try {
                tileDraw.draw((Graphics2D) g); //Draw every tile that is supposed to be shown onscreen
            } catch (IOException e) {
                throw new RuntimeException(e); //Throw possible errors
            }

            player.draw((Graphics2D) g); //Draw the player
            tileDistanceDraw.draw((Graphics2D) g); //Draw the score
            inventory.draw((Graphics2D) g); //Draw the hidden inventory
            dialogBox.draw((Graphics2D) g); //Draw the dialog box
        }
    }
}
