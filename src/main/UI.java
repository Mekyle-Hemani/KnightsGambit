package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    public static boolean isStarted = false;

    private static JFrame frame;

    public static void drawUI(int screenWidth,int screenHeight) {
        if (!isStarted && frame == null) {
            frame = new JFrame("Game UI");
            frame.setSize(screenWidth, screenHeight);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new BorderLayout());
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

            panel.add(startButton, BorderLayout.CENTER);
            frame.add(panel);
            frame.setVisible(true);
        }
    }
}
