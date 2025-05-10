package bankCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import de.hwr.wm.ds.DaoException;
import de.hwr.wm.ds.UserDataDAODBImpl;
import userLogin.LoginBackEnd;

public class BankCardBackEnd {
	UserDataDAODBImpl userDataDAODB = new UserDataDAODBImpl();
	ArrayList<Double> userBalanceArray;
	
	public static String generateIBAN() {
		
		Random random = new Random();
		int digit;
		String stringIban = "CY99 ";
		for (int i = 0; i < 18; i++) {
			digit = random.nextInt(10);
	        stringIban = stringIban + digit;
		
		}
		return stringIban;
	}
	
	public static String generateBankCardNumber() {
		Random random = new Random();
		int digit;
		String cardNumber = "";
		for (int i = 0; i < 16; i++) {
			digit = random.nextInt(10);
	        cardNumber = cardNumber + " " + digit;
		}
		return cardNumber;
	}
	
	public static String generateName() {
		String[] namesStrings = new String[] {"Anna Schneider", "Paul Müller", "Lisa Wagner",
				"Leon Fischer","Felix Mayer", "Amelie Richter", "Jürgen Schmidt", "Torsten Schwartz", "Charlotte Zimmermann",
				"Ingrid Storch", "Sophia Koch", "Elias Schäfer"};
		ArrayList<String> nameList = new ArrayList<>(Arrays.asList(namesStrings));
		Collections.shuffle(nameList);
		String randomName = nameList.get(0);
		
		return randomName;
	}
	public static String generateCSV() {
		int csvInt = new Random().nextInt(0,999);
		String csv = "" + csvInt;		
		if (csvInt <= 100) {
			csv = "0" + csvInt;
		}
		return csv;
		
	}

	public void payment() throws DaoException {
		String customer_ID = LoginBackEnd.customer_ID;
		String message = "Gebühr für neue Karte";
		String receiver_ID = "";
		userBalanceArray = userDataDAODB.getBalanceUserArray(customer_ID);
		if (userBalanceArray.isEmpty()) {
			throw new DaoException("Keine Kontostandsdaten vorhanden.");
		}
		double newBalance = userBalanceArray.get(userBalanceArray.size() - 1) - 20; // Letzter Wert des Array speichern
		userDataDAODB.uploadTransactionToDatabase(newBalance, customer_ID, message, receiver_ID, customer_ID);
	}
	

}
