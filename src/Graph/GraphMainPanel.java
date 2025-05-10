package Graph;

import de.hwr.wm.ds.DaoException;
import de.hwr.wm.ds.UserDataDAODBImpl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GraphMainPanel extends JPanel {
    UserDataDAODBImpl userDataDAODBImpl = new UserDataDAODBImpl();          //Instanz der Klasse für Methoden Zugriff der Datenbankverbindung

    private ArrayList<Double> userBalanceArray = new ArrayList<>();          //Neue ArrList zum speichern der Nutzer Kontostände
    private  final int MAX_BALANCE = 2000;                            // Klassenvariable, Maximaler Kontostand für den Graphen

    public GraphMainPanel() throws DaoException {                           //Konstruktor aktualisiert Daten neu und zeichnet graphen neu
        setPreferredSize(new Dimension(395, 295));
        setBackground(Color.lightGray);

        loadUserBalanceArray();                                             // Lädt die aktuellen Daten des Nutzers
        Timer timer = new Timer(30000, new ActionListener() {           // lädt alle 30 Sekunden Kontostände neu und zeichnet Graphen neu
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Double> oldData = new ArrayList<>(userBalanceArray); // Neue Instanz von ArrayList mit selben Daten wie USerBallanceArray
                    loadUserBalanceArray(); // Guthaben laden
                    if (!userBalanceArray.equals(oldData)) {
                        repaint();
                        System.out.println("Graph aktualisiert ...");
                    }
                } catch (DaoException ex) {
                    System.out.println("DAO Exception: " + ex.getMessage());
                }
            }
        });


        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {                     //Graphen erstellen
        super.paintComponent(g);

        if (userBalanceArray.isEmpty()) {
            userBalanceArray.add(0.0);                          // Leere Liste absichern, gegen Exception und Startpunkt 0
        }

        Graphics2D graphics2D = (Graphics2D) g;

        // Hintergrundfarbe setzen
        graphics2D.setColor(Color.decode("#3d3d3d"));
        graphics2D.fillRect(0, 0, getWidth(), getHeight());             //x = Horizontal, y = vertikal, zeichnet rechteck mit der Farbe

        // Achsen zeichnen
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawLine(50, getHeight() - 20, getWidth() - 20, getHeight() - 20); // X-Achse zeichnet Linie von (x1,y1) bis (x2,y2)
        graphics2D.drawLine(50, getHeight() - 20, 50, 20); // Y-Achse zeichnet genauso, nur für y-Achse

        // Beschriftung der Y-Achse in 200er Schritten
        graphics2D.setFont(new Font("Sans Serif", Font.BOLD, 12));
        for (int i = 0; i <= 10; i++) {                                                         //Index zählt von 0 bis 10
            int yPosition = getHeight() - 20 - i * ((getHeight() - 40) / 10);                   //Pro Index, Euro Beschr. erhöht sich um 200 (200*10 = 2000max)
            graphics2D.drawString(String.valueOf(i * 200), 17, yPosition);                 //Wandelt int in String um, zur Beschriftung
        }

        // Graph zeichnen
        graphics2D.setColor(Color.WHITE);

        int prevX = 50; // Startpunkt X für den ersten Punkt (0.0)
        int prevY = getYPosition(userBalanceArray.get(0));                                   // Y-Position für den ersten Punkt (0.0)
        graphics2D.fillOval(prevX - 3, prevY - 3, 6, 6);                    // Ersten Punkt zeichnen

        for (int i = 1; i < userBalanceArray.size(); i++) {
            int x = 50 + i * 20;                                                                // Abstand zwischen den Punkten
            int y = getYPosition(userBalanceArray.get(i));                                      // Berechnete Y-Position
            graphics2D.fillOval(x - 3, y - 3, 6, 6);                            // Punkt zeichnen
            graphics2D.drawLine(prevX, prevY, x, y);                                            // Linie zeichnen zwischen dem alten Punkt (prevX, prevY) und dem neuen (x,y)

            prevX = x;
            prevY = y;
        }
    }

    // Berechnet die Y-Position basierend auf dem Wert
     int getYPosition(double value) {
        if (value < 0 || value > MAX_BALANCE) {
            System.out.println("Warnung: Ungültiger Wert " + value + " im userBalanceArray!");
            value = Math.max(0, Math.min(value, MAX_BALANCE)); // Clamping
        }
        return (int) (getHeight() - 20 - (value / MAX_BALANCE) * (getHeight() - 40));
    }

    // Guthaben laden und den Graphen neu zeichnen
     void loadUserBalanceArray() throws DaoException {
        ArrayList<Double> balanceFromDb = userDataDAODBImpl.getBalanceUserArray();   // Kopie der Kontostände erstellen
        userBalanceArray.clear();                                                    // ArrListe leeren, um sie mit den aktuellen Werten füllen zu können
        userBalanceArray.add(0.0);                                                  // Immer 0.0 als ersten Wert einfügen, sonst Graph falsch
        if (balanceFromDb != null) {
            userBalanceArray.addAll(balanceFromDb);                                 // Alle Werte zum Array hinzufügen
        } else {
            System.err.println("Datenbank-Fehler: Keine Balance-Daten gefunden!");
        }
    }
}
