package main;
import javax.swing.JFrame;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame();
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
