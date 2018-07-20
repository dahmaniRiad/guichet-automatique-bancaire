package Carte;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import java.util.ArrayList;
import java.util.List;

public class SmartCard {
    private static CardTerminal terminal;

    private static long temps = 100;

    //constructeur

    public SmartCard(){

        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = new ArrayList<CardTerminal>();
        try {
            terminals = factory.terminals().list();
        } catch (CardException e) {
//            JOptionPane.showInternalMessageDialog(null,"Lecture de carte impossible ");
            e.printStackTrace();
        }
        terminal = (CardTerminal) terminals.get(0);
        temps = 400;
    }
    //methodes
    /* trosformer l'APDU en chaine de caractere */
    public static String toString(byte[] bytes) {
        StringBuffer sbTmp = new StringBuffer();
        for(byte b : bytes){
            sbTmp.append(String.format("%X", b));
        }
        return sbTmp.toString();
    }
    //les informations servant � identifier la carte
    public static String numeroCarte()throws NumeroCardException{
        try{
            Card card = terminal.connect("*");
            // recuperer le num�ro de la carte en bayte
            byte[] ATR = card.getATR().getBytes();
            // deconnecter la carte
            card.disconnect(true);
            // returner le numero de la carte apres le transfomer en String
            return SmartCard.toString(ATR) ;
        }catch(Exception ex)  {
  //          JOptionPane.showMessageDialog(null,"****Insere la carte correctement !! ****");
            ex.printStackTrace();
            throw new NumeroCardException() ;
        }
    }
    //savoir si une carte a �t� introduite dans le lecteur
    public static boolean insertionCarte() throws CardException {
            return terminal.isCardPresent();
    }
    //pour r�agir juste � l'insertion de la carte
    public static boolean attenteCartePresent() {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = new ArrayList<CardTerminal>();

        try {

            terminals = factory.terminals().list();

        } catch (Exception e) {

      //      JOptionPane.showMessageDialog(null,"Lecture de carte impossible "+e.getMessage());

            e.printStackTrace();

        }

        terminal = (CardTerminal) terminals.get(0);



        try {

            if(terminal.waitForCardPresent(temps)){
        //        JOptionPane.showMessageDialog(null,"Carte Inserer avec succes !!");
                return true;
            }
            else return false;

        } catch (CardException e) {



          //  JOptionPane.showMessageDialog(null,"Lecture de carte impossible ");

            e.printStackTrace();

            return false;

        }

    }


    public static boolean attenteCarteAbsent() {

        TerminalFactory factory = TerminalFactory.getDefault();

        List<CardTerminal> terminals = new ArrayList<CardTerminal>();

        try {

            terminals = factory.terminals().list();

        } catch (Exception e) {

            //JOptionPane.showMessageDialog(null,"Lecture de carte impossible ");

            e.printStackTrace();

        }

        terminal = (CardTerminal) terminals.get(0);



        try {

            if(terminal.waitForCardAbsent(temps)){
              //  JOptionPane.showMessageDialog(null, "La carte est desinserer avec succes !!");
                return true;
            }
            else return false;

        } catch (CardException e) {



            //JOptionPane.showMessageDialog(null,"Lecture de carte impossible ");

            e.printStackTrace();

            return false;

        }

    }
}
