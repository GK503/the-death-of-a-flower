package org.example;

import javax.imageio.ImageIO;
// import java.io.File;
import javax.swing.*;
import javax.swing.OverlayLayout;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuPanel extends JPanel {
    private final int width;
    private final int height;
    private Image backgroundImage;

    public MenuPanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setLayout(new OverlayLayout(this));
        setOpaque(false);
        setDoubleBuffered(true);

        backgroundImage = loadBackground();

        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
        backgroundLabel.setVerticalAlignment(JLabel.CENTER);
        add(backgroundLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setOpaque(true);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setAlignmentY(Component.TOP_ALIGNMENT);

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

        add(buttonPanel);
    }

    private Image loadBackground() {
        // Load from classpath resources instead of file system
        java.net.URL url = getClass().getResource("/assets/placeholder.png");
        try {
            return ImageIO.read(url);
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            return createGradientTestBackground();
        }
    }

    private Image createGradientTestBackground() {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setPaint(new GradientPaint(0, 0, new Color(20, 20, 50), 0, height, new Color(50, 50, 100)));
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(new Color(255, 255, 255, 100));
        for (int i = 0; i < 100; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int size = (int) (Math.random() * 3) + 1;
            g2d.fillOval(x, y, size, size);
        }
        g2d.dispose();
        return img;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(new Color(50, 50, 50, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}
