package userLogin;

import javax.swing.*;

import Hauptmenue.MainFrame;
import de.hwr.wm.ds.DaoException;
import de.hwr.wm.ds.OracleDsSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class LoginBackEnd {
	
	private static CardSwitcher cardSwitcher;	
	public static String customer_ID;
    private static JFrame currentFrame;

	
    public static void setCardSwitcher(CardSwitcher switcher) {
        cardSwitcher = switcher;
    }
    public static void setCurrentFrame(JFrame frame){
        currentFrame = frame;
    }
    public LoginBackEnd() {
    	
    }
    
    public static void activationCode(String codeCheck) {
        if (codeCheck == null || codeCheck.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Der Aktivierungscode darf nicht leer sein.");
            return;
        }
        
        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {
            String sql = "SELECT COUNT(*) FROM ACTIVATION_CODES WHERE CODES = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, codeCheck);               
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) { 
                        JOptionPane.showMessageDialog(null, "Aktivierungs Code akzeptiert");
                        removeActivationCode(codeCheck);
                        cardSwitcher.switchCards("2");
                    } else {
                        JOptionPane.showMessageDialog(null, "Der eingegebene Code ist ungültig.");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Zugriff auf die Datenbank: " + e.getMessage());
        }
    }	
    private static void removeActivationCode(String codeCheck) {
        if (codeCheck == null || codeCheck.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Der Aktivierungscode darf nicht leer sein.");
            return;
        }

        try (Connection connection = OracleDsSingleton.getInstance().getConnection()) {
            String sql = "DELETE FROM ACTIVATION_CODES WHERE CODES = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, codeCheck.trim());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Fehler beim Entfernen des Aktivierungscodes: " + e.getMessage());
            e.printStackTrace();
        }
    }
	private static String userID(String username) {
		Random random = new Random();
		String userID = username + random.nextInt();
		return userID;
		
	}
	public static void  addNewUser(String username, String password, String passwordCheck) {	
		
		int passwordLengthCheck = password.length();
		int usernameLengthCheck = username.length();

		if (!password.equals(passwordCheck)) {
			JOptionPane.showMessageDialog(null, "Passwörter stimmen nicht überein");
			return;
		}
		if (passwordLengthCheck < 8 || usernameLengthCheck < 6) {
			JOptionPane.showMessageDialog(null, "Passwort oder Benutzername ist nicht lang genug");
			return;
			
		}
	    PreparedStatement pstmt = null;
	    PreparedStatement pstmt2 = null;

	    try (Connection con = OracleDsSingleton.getInstance().getConnection()) {

	        String sql = "INSERT INTO CUSTOMER (CUSTOMER_ID, USER_NAME, PASSWORT) VALUES (?, ?, ?)";
	        pstmt = con.prepareStatement(sql);
	        
	        String customerID = userID(username);
	        
	        pstmt.setString(1, customerID);
	        pstmt.setString(2, username);
	        pstmt.setString(3, password);
	        
	        int rowsInserted = pstmt.executeUpdate();
	        if (rowsInserted > 0) {
	            JOptionPane.showMessageDialog(null, "Benutzer wurde erfolgreich erstellt.");
	            cardSwitcher.switchCards("3");
	        }
	        
	        String sql2 = "INSERT INTO TRANSACTIONS (CUSTOMER_ID, AMOUNT, T_NUMBER, ZWECK) VALUES (?, ?, ?, ?)";
	        pstmt2 = con.prepareStatement(sql2);	        	       	        
	        pstmt2.setString(1, customerID);
	        pstmt2.setString(2, "0");
	        pstmt2.setString(3, "0");
	        pstmt2.setString(4, null);

	        pstmt2.executeUpdate();
	        
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Fehler beim Einfügen in die Datenbank: " + e.getMessage());
	    } 
	}
	public static void loginParser(String username, String password) {

        try (Connection con = OracleDsSingleton.getInstance().getConnection()) {

        	String sql = "SELECT CUSTOMER_ID FROM CUSTOMER WHERE USER_NAME = ? AND PASSWORT = ?";
        	try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                    	customer_ID = rs.getString("CUSTOMER_ID");
                        new MainFrame();
                        JOptionPane.showMessageDialog(null, "LOGIN SUCCESS");
                    } else {
                        JOptionPane.showMessageDialog(null, "Login fehlgeschlagen");
                    }
                }
            }
        } catch (SQLException | DaoException e) {
            e.printStackTrace();
            System.out.println("e");
        }
    }

}
