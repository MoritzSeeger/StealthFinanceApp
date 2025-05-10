package de.hwr.wm.ds;

import userLogin.LoginBackEnd;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class UserDataDAODBImpl implements UserData {

    private static String customer_ID = LoginBackEnd.customer_ID;
    @Override
    public ArrayList<Double> getBalanceUserArray() throws DaoException {                                    // ließt Kontostände von eingeloggtem Nutzer ein
        ArrayList<Double> balanceArray = new ArrayList<>();                                             // Initialisiere als leere Liste

        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
                                                                                                         // SQL-Abfrage, um die Werte aus der Tabelle CUSTOMER_VALUES zu holen
            String sql = "SELECT VALUE FROM CUSTOMER_VALUES WHERE CUSTOMER_ID = ? ORDER BY VALUE_INDEX";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, customer_ID); // Setze die 'customer_ID' für die Abfrage

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Hole den Wert aus der Spalte 'VALUE'
                        Double value = rs.getDouble("VALUE");

                        // Füge den Wert zur ArrayList hinzu
                        balanceArray.add(value);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Abrufen des Kontostands: " + e.getMessage());
        }

        return balanceArray; // Gibt die ArrayList zurück (leere Liste, falls keine Daten vorhanden)
    }

    @Override
    public ArrayList<Double> getBalanceUserArray(String customer_ID) throws DaoException {                      //Ließt Kontostände von einem beliebigen Nutzer ein
        ArrayList<Double> balanceArray = new ArrayList<>(); // Initialisiere als leere Liste

        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
            // SQL-Abfrage, um die Werte aus der Tabelle CUSTOMER_VALUES zu holen
            String sql = "SELECT VALUE FROM CUSTOMER_VALUES WHERE CUSTOMER_ID = ? ORDER BY VALUE_INDEX";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, customer_ID); // Setze die 'customer_ID' für die Abfrage

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Hole den Wert aus der Spalte 'VALUE'
                        Double value = rs.getDouble("VALUE");

                        // Füge den Wert zur ArrayList hinzu
                        balanceArray.add(value);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Abrufen des Kontostands: " + e.getMessage());
        }

        return balanceArray; // Gibt die ArrayList zurück (leere Liste, falls keine Daten vorhanden)
    }


    public void addBalanceFromBank(Double newBalance, LocalDate t_Date , String t_Sender_ID) throws DaoException {      //Fügt Guthaben dem Konto von einer anderen Bank hinzu per IBAN
        // Überprüfen, ob der neue Balance-Wert gültig ist
        if (newBalance == null) {
            JOptionPane.showMessageDialog(null, "Der Balance-Wert darf nicht leer sein.");
            return;
        }

        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
            // Schritt 1: Prüfen, wie viele Werte bereits für den Kunden gespeichert sind
            String countSql = "SELECT COUNT(*) AS count FROM CUSTOMER_VALUES WHERE CUSTOMER_ID = ?";
            int currentCount = 0;

            try (PreparedStatement countStmt = con.prepareStatement(countSql)) {
                countStmt.setString(1, customer_ID);
                try (ResultSet rs = countStmt.executeQuery()) {
                    if (rs.next()) {
                        currentCount = rs.getInt("count");
                    }
                }
            }

            // Schritt 2: Nächsten Index bestimmen
            int nextIndex = currentCount + 1;

            // Schritt 3: Neuen Wert einfügen
            String insertSql = "INSERT INTO CUSTOMER_VALUES (CUSTOMER_ID, VALUE, T_USE, T_DATE, T_SENDER_ID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
                insertStmt.setString(1, customer_ID); // Kundennummer
                insertStmt.setDouble(2, newBalance); // Neuer Balance-Wert
                insertStmt.setString(3, "Bank Überweisung");
                insertStmt.setDate(4, Date.valueOf(t_Date));
                insertStmt.setString(5, t_Sender_ID);

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Balance erfolgreich hinzugefügt.");
                } else {
                    JOptionPane.showMessageDialog(null, "Fehler beim Hinzufügen des neuen Werts.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Einfügen des Kontostands: " + e.getMessage());
        }
    }

    @Override
    public void uploadTransactionToDatabase(Double newBalance, String customer_ID, String message, String T_Receiver_ID, String T_SENDER_ID ) throws DaoException { //Sendet Geld von einem Nutzer zum anderen
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        // Überprüfen, ob der neue Balance-Wert gültig ist
        if (newBalance == null) {
            JOptionPane.showMessageDialog(null, "Der Balance-Wert darf nicht leer sein.");
            return;
        }

        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
            // Schritt 1: Prüfen, wie viele Werte bereits für den Kunden gespeichert sind
            String countSql = "SELECT COUNT(*) AS count FROM CUSTOMER_VALUES WHERE CUSTOMER_ID = ?";
            int currentCount = 0;

            // Schritt 2: Nächsten Index bestimmen
            int nextIndex = currentCount + 1;

            // Schritt 3: Neuen Wert einfügen
            String insertSql = "INSERT INTO CUSTOMER_VALUES (CUSTOMER_ID, VALUE, T_USE, T_DATE, T_TIME, T_RECEIVER_ID, T_SENDER_ID ) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
                insertStmt.setString(1, customer_ID); // Kundennummer
                insertStmt.setDouble(2, newBalance); // Neuer Balance-Wert
                insertStmt.setString(3, message); // Verwendungszweck
                insertStmt.setDate(4, Date.valueOf(date)); // Aktuelles Dartum
                insertStmt.setTime(5, Time.valueOf(time)); // Aktuelle Zeit
                insertStmt.setString(6, T_Receiver_ID); //
                insertStmt.setString(7, T_SENDER_ID);

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Überweisung erfolgreich gelistet.");
                } else {
                    JOptionPane.showMessageDialog(null, "Fehler beim listen der Überweisung.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Einfügen des Kontostands: " + e.getMessage());
        }
    }

    public ArrayList<String> getUser_TransactionMessage(String customer_ID) throws DaoException{
        ArrayList<String> message = new ArrayList<>();
        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
            // SQL-Abfrage, um die Werte aus der Tabelle CUSTOMER_VALUES zu holen
            String sql = "SELECT T_USE FROM CUSTOMER_VALUES WHERE CUSTOMER_ID = ? ORDER BY VALUE_INDEX";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, customer_ID); // Setze die 'customer_ID' für die Abfrage

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Hole den Wert aus der Spalte 'T_USE'
                        message.add(rs.getString("T_USE"));
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Abrufen des Abrufens des Verwendungszwecks: " + e.getMessage());
        }
        return message;
    }

    public ArrayList<String> getUser_T_Sender_ID(String customer_ID) throws DaoException{
        ArrayList<String> t_SENDER_ID = new ArrayList<>();
        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
            // SQL-Abfrage, um die Werte aus der Tabelle CUSTOMER_VALUES zu holen
            String sql = "SELECT T_SENDER_ID FROM CUSTOMER_VALUES WHERE CUSTOMER_ID = ? ORDER BY VALUE_INDEX";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, customer_ID); // Setze die 'customer_ID' für die Abfrage

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Hole den Wert aus der Spalte 'T_USE'
                        t_SENDER_ID.add(rs.getString("T_SENDER_ID"));
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Abrufen des Abrufens des Sender_ID: " + e.getMessage());
        }
        return t_SENDER_ID;
    }

    public ArrayList<String> getUser_T_Receiver_ID(String customer_ID) throws DaoException{
        ArrayList<String> t_Receiver_ID = new ArrayList<>();
        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
            // SQL-Abfrage, um die Werte aus der Tabelle CUSTOMER_VALUES zu holen
            String sql = "SELECT T_RECEIVER_ID FROM CUSTOMER_VALUES WHERE CUSTOMER_ID = ? ORDER BY VALUE_INDEX";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, customer_ID); // Setze die 'customer_ID' für die Abfrage

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Hole den Wert aus der Spalte 'T_USE'
                        t_Receiver_ID.add(rs.getString("T_RECEIVER_ID"));
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Abrufen des Abrufens des EMpfängers: " + e.getMessage());
        }
        return t_Receiver_ID;
    }

    public  String getIban() throws DaoException{                                       //Iban des Nutzers abrufen
        String iban = "";
        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
            // SQL-Abfrage, um die Werte aus der Tabelle CUSTOMER zu holen
            String sql = "SELECT IBAN FROM CUSTOMER WHERE CUSTOMER_ID = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, customer_ID); // Setze die 'customer_ID' für die Abfrage

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Hole den Wert aus der Spalte 'IBAN'
                        iban = rs.getString("IBAN");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Abrufen der IBAN: " + e.getMessage());
        }
        return iban;
    }

    public String getUser_Name() throws DaoException{
        String user_Name = "";
        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
            // SQL-Abfrage, um die Werte aus der Tabelle CUSTOMER zu holen
            String sql = "SELECT USER_NAME FROM CUSTOMER WHERE CUSTOMER_ID = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, customer_ID); // Setze die 'customer_ID' für die Abfrage

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Hole den Wert aus der Spalte 'USER_NAME'
                        user_Name = rs.getString("USER_NAME");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Abrufen des USER_NAME: " + e.getMessage());
        }
        return user_Name;
    }
}
