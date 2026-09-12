package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class App {

    private static JLayeredPane layeredPane;
    private static Image backgroundImage;
    private static boolean creditsOpen = false;

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
            frame.addKeyListener(new KeyListener() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        if (creditsOpen) {
                            Component[] comps = layeredPane.getComponents();
                            for (Component c : comps) {
                                if (c.getName() != null && c.getName().equals("credits")) {
                                    layeredPane.remove(c);
                                    break;
                                }
                            }
                            layeredPane.revalidate();
                            layeredPane.repaint();
                            creditsOpen = false;
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {}

                @Override
                public void keyTyped(KeyEvent e) {}
            });
            frame.setVisible(true);
            frame.requestFocusInWindow();
        });
    }

    public static JPanel createMenuPanel(int width, int height) {
        JPanel root = new JPanel(new BorderLayout());
        root.setPreferredSize(new Dimension(width, height));

        backgroundImage = loadBackground(width, height);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(width, height));

        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, width, height);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
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

        JButton creditsButton = createMenuButton("CREDITS");
        creditsButton.addActionListener(e -> {
            System.out.println("Credits Clicked");
            openCredits(width, height);
        });

        JButton quitButton = createMenuButton("QUIT");
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(startButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(quitButton);

        int panelWidth = 300;
        int panelHeight = 260;
        buttonPanel.setBounds(50, 50, panelWidth, panelHeight);
        layeredPane.add(buttonPanel, Integer.valueOf(1));

        root.add(layeredPane, BorderLayout.CENTER);
        return root;
    }

    private static void openCredits(int width, int height) {
        JPanel creditsPanel = new JPanel();
        creditsPanel.setLayout(new GridLayout(4, 1, 10, 10));
        creditsPanel.setOpaque(false);
        creditsPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        creditsPanel.setName("credits");

        creditsPanel.add(new JLabel("CREDITS"));
        creditsPanel.add(new JLabel("Art: Chibi|Neko and Rimi"));
        creditsPanel.add(new JLabel("Story: Chibi|Neko and Rimi"));
        creditsPanel.add(new JLabel("Coding: Anøm"));
        creditsPanel.add(new JLabel("Music: SgtSlippery"));

        creditsPanel.setBounds(50, 50, 300, 260);
        layeredPane.add(creditsPanel, Integer.valueOf(2));
        layeredPane.revalidate();
        layeredPane.repaint();
        creditsOpen = true;
    }

    public static Image loadBackground(int width, int height) {
        java.net.URL url = App.class.getResource("/assets/TitleScreen/Library_Soft.png");
        try {
            BufferedImage originalImage = ImageIO.read(url);
            BufferedImage resizedImage = new BufferedImage(
                width,
                height,
                originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType()
            );
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            g2d.dispose();
            return resizedImage;
        } catch (Exception e) {
            System.err.println("Failed to load background image: " + e.getMessage());
            return createFallbackBackground(width, height);
        }
    }

    private static Image createFallbackBackground(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setPaint(new GradientPaint(0, 0, new Color(20, 20, 50), 0, height, new Color(50, 50, 100)));
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        return img;
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
