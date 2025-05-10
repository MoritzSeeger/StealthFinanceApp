package Graph;

import de.hwr.wm.ds.DaoException;
import de.hwr.wm.ds.UserDataDAODBImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GraphPanel extends JPanel {
    JPanel balance_Panel, graph_Panel, main_Panel;
    JLabel accountBalance_Label;
    ArrayList<Double> userBalanceArray = new ArrayList<>();
    private double oldBalance = 0;
    public GraphPanel() throws DaoException {

        Timer timer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkArrayBalance();                                //Kontostand aktualisieren
                    double neuerKontostand = checkArrayBalance();           // Kontostand übernehmen
                    if (neuerKontostand != oldBalance){                 //wenn sich der Kontostand ändert, bekommt oldBalance den Wert und neuerKontostand zeigt ihn im Label an
                        oldBalance = neuerKontostand;
                        repaint();
                    }
                    accountBalance_Label.setText("Kontostand: " + neuerKontostand + "€");           //Kontostand Anzeige
                } catch (DaoException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("repaint Kontostand ...");
            }
        });
        timer.start();

        accountBalance_Label = new JLabel("Kontostand: " + checkArrayBalance() +"€");
        accountBalance_Label.setFont(new Font("Sarif Sans",Font.BOLD,18));
        accountBalance_Label.setForeground(Color.WHITE);

        main_Panel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        main_Panel.setPreferredSize(new Dimension(395,390));
        main_Panel.setBackground(Color.decode("#3d3d3d"));

        balance_Panel = new JPanel();
        balance_Panel.setPreferredSize(new Dimension(395,95));
        balance_Panel.setBackground(Color.decode("#3d3d3d"));
        balance_Panel.add(accountBalance_Label);

        graph_Panel = new GraphMainPanel();
        graph_Panel.setPreferredSize(new Dimension(395,295));
        graph_Panel.setBackground(Color.decode("#3d3d3d"));

        main_Panel.add(balance_Panel);
        main_Panel.add(graph_Panel);

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(main_Panel);

    }
    public double checkArrayBalance() throws DaoException {
        UserDataDAODBImpl userDataDAODB = new UserDataDAODBImpl();          //Instanz
        userBalanceArray = userDataDAODB.getBalanceUserArray();                // kopie des UserDate in ein Arr
        double currBalance;
        if (userBalanceArray.size() == 0){
            currBalance = 0;
        }else {
            currBalance = userBalanceArray.getLast();                       //Gibt letzten Index des Arr wieder, für Kontostand
        }
        return currBalance;
    }
}
