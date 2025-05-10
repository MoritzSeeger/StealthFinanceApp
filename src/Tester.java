import java.util.ArrayList;
import java.util.Random;

public class Tester {
    private  static String createIBAN(){
        Random random = new Random();
        char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        ArrayList<String> iban = new ArrayList<>();
        String completIBAN = "";

        // Es werden zwei zufällige Buchstaben zum Array hinzugefügt
        for (int i = 0; i < 2; i++){
            iban.add(Character.toString(alphabet[i]));
        }

        // eine Iban hat zwei Buchstaben am Anfang und dahinter 20 Zahlen, es werden 20 zufällige Zahlen 0 - 9 zum Array hinzugefügt.
        // Nach den ersten vier Einträgen wird eine Leerzeile hinzugefügt, dannach alle 5, weil die leerzeile auch als Wertzählz.
        int index = 0;
        while (index < 20) {
            // Leerzeichen einfügen
            if (iban.size() == 4 || (iban.size() > 4 && (iban.size() - 4) % 5 == 0)) {
                iban.add(" ");
            }
            // Zufällige Zahl hinzufügen
            iban.add(String.valueOf(random.nextInt(10)));
            index++;
        }


        // Das Array iban wird an den String completeIBAN übergeben, dann wird er als return von der Methode weitergegeben
        for (int i = 0; i < iban.size(); i++){
            completIBAN += iban.get(i);
        }
        return  completIBAN;
    }
    public static void main(String[] args) {
        System.out.println("New IBAN: "+createIBAN());
    }
}
