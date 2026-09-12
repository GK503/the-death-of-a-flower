package org.example;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class App {

    private static Image backgroundImage;

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(true);
            frame.setUndecorated(true);

            GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (device.isFullScreenSupported()) {
                device.setFullScreenWindow(frame);
            } else {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }

            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            JPanel menuPanel = createMenuPanel(screen.width, screen.height);
            frame.setContentPane(menuPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static JPanel createMenuPanel(int width, int height) {
        JPanel root = new JPanel(new BorderLayout());
        root.setPreferredSize(new Dimension(width, height));

        backgroundImage = loadBackground();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));

        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, width, height);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JButton startButton = createMenuButton("START");
        startButton.addActionListener(e -> {
            System.out.println("Start game clicked!");
            // TODO: Start game logic
        });

        JButton settingsButton = createMenuButton("SETTINGS");
        settingsButton.addActionListener(e -> {
            System.out.println("Settings clicked!");
            // TODO: Open settings
        });

        JButton quitButton = createMenuButton("QUIT");
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(quitButton);

        int panelWidth = 300;
        int panelHeight = 220;
        buttonPanel.setBounds(50, 50, panelWidth, panelHeight);
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        root.add(layeredPane, BorderLayout.CENTER);
        return root;
    }

    public static Image loadBackground() {
        java.net.URL url = App.class.getResource("/assets/placeholder.png");
        try {
            return ImageIO.read(url);
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

    private static JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}