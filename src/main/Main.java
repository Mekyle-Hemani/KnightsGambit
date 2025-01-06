package main;
import javax.swing.JFrame;
import java.awt.*;
import java.io.IOException;

public class Main {
    private Thread thread;
    public static JFrame window = new JFrame();
    public static void main(String[] args) throws IOException, FontFormatException {
        window.setName("Knight's Gambit");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
