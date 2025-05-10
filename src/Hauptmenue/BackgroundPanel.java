package Hauptmenue;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // Konstruktor für das Laden des Bildes
    public BackgroundPanel(String imagePath) {
        // Lade das Bild aus dem Klassenpfad
        java.net.URL imageURL = getClass().getResource(imagePath);
        if (imageURL != null) {
            backgroundImage = new ImageIcon(imageURL).getImage();
        } else {
            // Falls das Bild nicht gefunden wird, eine Fehlermeldung ausgeben
            System.err.println("Das Bild konnte nicht geladen werden: " + imagePath);
        }
    }

    // Hintergrundbild rendern
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}