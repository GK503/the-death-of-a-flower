    package org.example;

    import javax.swing.*;
    import java.awt.*;
    // import java.awt.image.BufferedImage;
    // import java.awt.image.RenderedImage;
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
                frame.add(menuPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.validate();
                frame.repaint();
            });
        }

        public static JPanel createMenuPanel(int width, int height) {

            JPanel menuPanel = new JPanel();
            menuPanel.setPreferredSize(new Dimension(width, height));
            menuPanel.setLayout(new OverlayLayout(menuPanel));
            menuPanel.setOpaque(false);
            menuPanel.setDoubleBuffered(true);

            backgroundImage = loadBackground();

            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
            backgroundLabel.setVerticalAlignment(JLabel.CENTER);
            menuPanel.add(backgroundLabel);

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

            menuPanel.add(buttonPanel);
            return menuPanel;
        }

        public static Image loadBackground() {
            // Load from classpath resources instead of file system
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
            button.setContentAreaFilled(true);
            button.setOpaque(true);
            button.setBackground(new Color(50, 50, 50, 180));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            return button;
        }
    }
