package userLogin;

import java.io.BufferedReader;
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoginBackEnd {
	
	private static final String USERDATA = "USERDATALIST.txt";
	private static final String ACTIVATIONCODELIST = "ACTIVATIONCODELIST.txt";
	private static String line;
	private static String delimiter = ","; 
	private static CardSwitcher cardSwitcher;
	private static boolean isTrue;
	
    public static void setCardSwitcher(CardSwitcher switcher) {
        cardSwitcher = switcher;
    }

	public static void activationCode(String codeCheck) {
		
			isTrue = false;

			try (BufferedReader bReader = new BufferedReader(new FileReader(ACTIVATIONCODELIST))){
				while((line = bReader.readLine()) !=null) {
					if(codeCheck.equals(line.trim())) {
						JOptionPane.showMessageDialog(null,"Aktivierungscode akzeptiert");
						removeActivationCode(codeCheck);
						isTrue = true;
						cardSwitcher.switchCards("2");					
						break;
						}
					}
				if (!isTrue) {
					JOptionPane.showMessageDialog(null, "Der Code: " + codeCheck + " :ist nicht gültig, versuchen Sie es bitte nochmal");
				}
				
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null,"System Fehler --- Bitte kontaktieren Sie den Support" + e.getMessage());
			}
		}	
	private static void removeActivationCode(String codeCheck) {
		try {		
			List <String> lines = 	Files.readAllLines(Paths.get("ACTIVATIONCODELIST.txt"));
			List <String> newCodes = new ArrayList<String>();
			for (String line : lines) {
				if (!line.equals(codeCheck)) {
					newCodes.add(line);	
				}
			}
			Files.write(Paths.get("ACTIVATIONCODELIST.txt"), newCodes);

			
		} catch (Exception e) {
			System.err.println("Error 002");
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
		String userID = userID(username);

		
		try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(USERDATA, true))){		
			bWriter.write(username+ ","+ password+","+userID);
			bWriter.newLine();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Upsi es ist ein fehler aufgetreten :(" + e.getMessage());
			return;
		}
		JOptionPane.showMessageDialog(null,"Benutzer wurde erfogreich erstellt");
		JOptionPane.showMessageDialog(null, "IDENTIFIKATION-CODE ABSCHREIBEN UND GEHEIM BEHALTEN: "+userID, "IDENTIFIKATION-CODE", 2);
		cardSwitcher.switchCards("1");
	}
	public static void loginParser(String username, String password) {
		
		isTrue = false;
		
		try (BufferedReader bReader = new BufferedReader(new FileReader(USERDATA))){
			while ((line = bReader.readLine()) !=null) {
				String[] column = line.split(delimiter); 
				if (column[0].equals(username.trim()) && column[1].equals(password.trim())) {
					JOptionPane.showMessageDialog(null, "LOGIN SUCCESS");
					isTrue = true;
					break;
				}				
			}
			if(!isTrue) {
				JOptionPane.showMessageDialog(null, "Login fehlgeschlagen");
			}	
		}catch (IOException e1) {
			e1.printStackTrace();
		} 
					
	}

}


