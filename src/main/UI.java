package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    public static boolean isStarted = false;

    private static JFrame frame;

    public static void drawUI(int screenWidth, int screenHeight) {
        if (!isStarted && frame == null) {
            frame = new JFrame("Knights Gambit");
            frame.setSize(screenWidth, screenHeight);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            // Create a panel with FlowLayout
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            // Add vertical space above the button by adding an empty panel
            JPanel spacer = new JPanel();
            spacer.setPreferredSize(new Dimension(0, screenHeight / 6)); // Adjust the value to move the button up or down
            panel.add(spacer);

            JButton startButton = new JButton("Start");
            startButton.setFont(new Font("Arial", Font.BOLD, 32));

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isStarted = true;
                    frame.setVisible(false);
                    frame.dispose();
                    frame = null;
                }
            });

            panel.add(startButton); // Add button to the panel
            frame.add(panel);
            frame.setVisible(true);
        }
    }
}
