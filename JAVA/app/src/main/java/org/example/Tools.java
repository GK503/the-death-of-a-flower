package org.example;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

public class Tools {
    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(100, 50));
        button.addActionListener(e -> System.out.println("BUTTON CLICKED"));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        return button;
    }
}
